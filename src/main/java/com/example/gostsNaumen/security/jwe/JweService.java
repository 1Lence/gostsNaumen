package com.example.gostsNaumen.security.jwe;

import com.example.gostsNaumen.exception.BusinessException;
import com.example.gostsNaumen.exception.ErrorCode;
import com.example.gostsNaumen.security.dto.JwtAuthDto;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.DirectDecrypter;
import com.nimbusds.jose.crypto.DirectEncrypter;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

/**
 * Сервис для работы с JWE токенами.
 * Предоставляет функции для генерации, валидации, обновления токенов,
 * а также извлечения данных (например, email) из токена.
 * Использует секретные ключи, загружаемые из конфигурации приложения,
 * для подписи (JWS) и шифрования (JWE) токенов.
 */
@Service
public class JweService {
    private final String jwsSecret;
    private final String jweSecret;
    private final Integer expireMinutes;
    private final Logger log = LoggerFactory.getLogger(JweService.class);

    public JweService(
            @Value("${app.jws.secret}") String jwsSecret,
            @Value("${app.jwe.secret}") String jweSecret,
            @Value("${app.expireMinutes}") Integer expireMinutes
    ) {
        this.jwsSecret = jwsSecret;
        this.jweSecret = jweSecret;
        this.expireMinutes = expireMinutes;
    }

    /**
     * Создаёт секретный ключ для шифрования на основе {@code jweSecret}.
     * Ожидается, что {@code jweSecret} закодирован в Base64 и представляет собой 256-битный ключ.
     *
     * @return Секретный ключ для шифрования.
     */
    private SecretKey getEncryptionKey() {
        byte[] keyBytes = Decoders.BASE64.decode(jweSecret);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    /**
     * Создаёт секретный ключ для подписи на основе {@code jwsSecret}.
     * Ожидается, что {@code jwsSecret} закодирован в Base64 и представляет собой 256-битный ключ.
     *
     * @return Секретный ключ для подписи.
     */
    private SecretKey getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(jwsSecret);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    /**
     * Генерирует JWE-токены (access и refresh) для указанного пользователя.
     *
     * <p>Этот метод генерирует два токена JWE: один для аутентификации,
     * и второй как токен обновления.</p>
     *
     * <p>Время жизни токена необходимо изменить.</p>
     *
     * @param email почта пользователя.
     * @param id    айди пользователя.
     * @return Объект {@link JwtAuthDto}, содержащий сгенерированные токены и ID пользователя.
     * @throws JOSEException в случае ошибки при создании токена.
     */
    public JwtAuthDto generateAuthToken(String email, Long id) throws JOSEException {
        return new JwtAuthDto(
                generateJweToken(email, expireMinutes),
                generateJweToken(email, expireMinutes),
                id);
    }

    /**
     * Извлекает почту пользователя из JWE токена.
     *
     * @param token JWE токен.
     * @return почта пользователя, извлечённая из токена, или {@code null}, если токен недействителен
     * или произошла ошибка при извлечении.
     * <p>
     * Пользователю ничего не говорится, т.к. ошибки связанные с токеном могут говорить о попытке взлома.
     */
    public String getEmailFromToken(String token) {
        try {
            SignedJWT signedJWT = parseJweToSignedJWT(token);

            String subject = signedJWT.getJWTClaimsSet().getSubject();

            if (subject == null) {
                throw new BusinessException(ErrorCode.INVALID_TOKEN);
            }

            return signedJWT.getJWTClaimsSet().getSubject();

        } catch (ParseException | JOSEException e) {
            log.error("Не удалось получить почту из JWE токена", e);
            throw new BusinessException(ErrorCode.INVALID_TOKEN);
        }
    }

    /**
     * Проверяет действительность JWE-токена.
     *
     * <p>Выполняет следующие проверки:</p>
     * <ul>
     *   <li>Расшифровка токена JWE.</li>
     *   <li>Проверка подписи внутреннего JWS токена.</li>
     *   <li>Проверка срока действия токена.</li>
     * </ul>
     *
     * @param token JWE токен для валидации.
     * @return {@code true}, если токен действителен, {@code false} в противном случае.
     */
    public boolean validateJweToken(String token) {
        try {
            SignedJWT signedJWT = parseJweToSignedJWT(token);

            if (signedJWT.verify(new MACVerifier(getSigningKey()))) {

                JWTClaimsSet claimsSet = signedJWT.getJWTClaimsSet();
                Date expirationTime = claimsSet.getExpirationTime();

                if (expirationTime != null && new Date().after(expirationTime)) {
                    log.error("JWE токен истёк.");
                    return false;
                }
                return true;
            } else {
                log.error("Верификация JWS сигнатуры не пройдена.");
            }
        } catch (Exception exception) {
            log.error("Ошибка валидации JWE токена.", exception);
        }
        return false;
    }

    /**
     * Обновляет основной токен, используя токен обновления.
     *
     * <p>Генерирует новый основной токен для указанного пользователя, сохраняя переданный токен обновления.</p>
     *
     * @param email        почта пользователя.
     * @param refreshToken Существующий токен обновления.
     * @param id           айди пользователя.
     * @return Объект {@link JwtAuthDto}, содержащий новый основной токен, старый токен обновления и айди пользователя.
     * @throws JOSEException в случае ошибки при создании токена.
     */
    public JwtAuthDto refreshBaseToken(String email, String refreshToken, Long id) throws JOSEException {
        return new JwtAuthDto(
                generateJweToken(email, expireMinutes),
                refreshToken,
                id);
    }

    /**
     * Генерирует зашифрованный JWE-токен с указанной почтой и временем жизни.
     *
     * <p>Токен подписывается с использованием {@link #getSigningKey()} JWS,
     * затем шифруется с использованием {@link #getEncryptionKey()} JWE.
     * Алгоритмы: HS256 для подписи, DIR и A256GCM для шифрования.</p>
     *
     * @param email         почта пользователя, который будет установлен в поле subject токена.
     * @param expireMinutes Время жизни токена в минутах.
     * @return JWE токен в формате String.
     * @throws JOSEException         в случае ошибки при создании токена.
     * @throws IllegalStateException если длина ключа шифрования некорректна для A256GCM.
     */
    private String generateJweToken(String email, int expireMinutes) throws JOSEException {
        Date expDate = Date.from(LocalDateTime.now().plusMinutes(expireMinutes)
                .atZone(ZoneId.systemDefault()).toInstant());

        JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                .subject(email)
                .expirationTime(expDate)
                .build();

        SignedJWT signedJWT = new SignedJWT(new JWSHeader(JWSAlgorithm.HS256), claimsSet);

        signedJWT.sign(new MACSigner(getSigningKey()));

        JWEHeader jweHeader = new JWEHeader.Builder(JWEAlgorithm.DIR, EncryptionMethod.A256GCM)
                .contentType("JWT")
                .build();

        SecretKey encryptionKey = getEncryptionKey();

        if (encryptionKey.getEncoded().length != 32) {
            throw new IllegalStateException("Ключ шифрования должен быть 256 бит (32 байта) для A256GCM.");
        }

        JWEObject jweObject = new JWEObject(jweHeader, new Payload(signedJWT));
        jweObject.encrypt(new DirectEncrypter(encryptionKey));

        return jweObject.serialize();
    }

    /**
     * Расшифровывает JWE токен и возвращает внутренний JWS-токен как {@link SignedJWT}.
     *
     * @param jweString JWE токен в формате String.
     * @return {@link SignedJWT}, содержащийся внутри JWE, или {@code null},
     * если токен не удалось расшифровать, или его заголовок не указан как JWT.
     * @throws ParseException        в случае ошибки парсинга JWE.
     * @throws JOSEException         в случае ошибки при расшифровке JWE.
     * @throws IllegalStateException если длина ключа дешифрования некорректна для A256GCM.
     */
    private SignedJWT parseJweToSignedJWT(String jweString) throws ParseException, JOSEException {
        JWEObject jweObject = JWEObject.parse(jweString);

        SecretKey decryptionKey = getEncryptionKey();
        if (decryptionKey.getEncoded().length != 32) {
            throw new IllegalStateException("Ключ дешифрования должен быть 256 бит (32 байта) для A256GCM.");
        }

        jweObject.decrypt(new DirectDecrypter(decryptionKey));

        if (!"JWT".equals(jweObject.getHeader().getContentType())) {
            log.error("Ожидается, что Header JWE будет представлять собой JWT");
            throw new BusinessException(ErrorCode.INVALID_TOKEN);
        }

        return SignedJWT.parse(jweObject.getPayload().toString());
    }
}