package com.example.gostsNaumen.security.jwe;

import com.example.gostsNaumen.security.dto.JwtAuthDto;
import com.nimbusds.jose.JOSEException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * Тестирование логики JWE сервиса.
 */
@ExtendWith(MockitoExtension.class)
class JweServiceTest {
    /**
     * Сервис для работы с JWE.
     * Используется для шифрования и расшифровки токенов.
     */
    private final JweService jweService;

    /**
     * Секрет для подписи токена
     */
    private final static String SECRET = "tPpMbX+5QkX1qKp3iPh4mfrc5D0F3eG9HvA2BcD4Efg";

    public JweServiceTest() {
        this.jweService = new JweService(
                SECRET,
                SECRET,
                1000
        );
    }

    /**
     * Проверяет успешную генерацию токена без возникновения исключений.
     * <p>
     * Сценарий:
     * <ol>
     *     <li>С помощью {@link JweService#generateAuthToken(String, Long)} генерируется JWT-токен с указанным email и ID.</li>
     *     <li>Из сгенерированного токена извлекается email с помощью {@link JweService#getEmailFromToken(String)}.</li>
     *     <li>Проверяется, что извлечённый email совпадает с переданным при генерации.</li>
     *     <li>Проверяется валидность токена с помощью {@link JweService#validateJweToken(String)}.</li>
     * </ol>
     * <p>
     * Ожидается, что токен будет успешно сгенерирован, email будет извлечён корректно и токен будет признан действительным.
     *
     * @throws JOSEException если возникает ошибка при генерации или обработке токена
     */
    @Test
    void shouldGenerateTokenWithoutException() throws JOSEException {
        JwtAuthDto jwtAuthenticationDto = jweService.generateAuthToken("example@example.com", 1L);
        Assertions.assertEquals(
                "example@example.com",
                jweService.getEmailFromToken(jwtAuthenticationDto.token())
        );
        Assertions.assertTrue(jweService.validateJweToken(jwtAuthenticationDto.token()));
    }

    /**
     * Проверяет извлечение email из токена без возникновения исключений.
     * <p>
     * Сценарий:
     * <ol>
     *     <li>С помощью {@link JweService#generateAuthToken(String, Long)} генерируется JWT-токен с указанным email.</li>
     *     <li>Из сгенерированного токена извлекается email с помощью {@link JweService#getEmailFromToken(String)}.</li>
     * </ol>
     * <p>
     * Ожидается, что извлечённый email совпадает с тем, который был использован при генерации токена.
     *
     * @throws JOSEException если возникает ошибка при генерации или обработке токена
     */
    @Test
    void shouldGetEmailFromTokenWithoutException() throws JOSEException {
        JwtAuthDto jwtAuthenticationDto = jweService.generateAuthToken("example@example.com", 1L);
        Assertions.assertEquals(
                "example@example.com",
                jweService.getEmailFromToken(jwtAuthenticationDto.token())
        );
    }

    /**
     * Проверяет сценарий положительной валидации JWE-токена.
     * <p>
     * Сценарий:
     * <ol>
     *     <li>С помощью {@link JweService#generateAuthToken(String, Long)} генерируется действительный JWT-токен.</li>
     *     <li>Сгенерированный токен передается в метод {@link JweService#validateJweToken(String)}.</li>
     * </ol>
     * <p>
     * Ожидается, что метод вернет {@code true}, подтверждая, что токен действителен.
     *
     * @throws JOSEException если возникает ошибка при генерации или валидации токена (например, подпись, расшифровка)
     */
    @Test
    void validateJweToken() throws JOSEException {
        JwtAuthDto jwtAuthenticationDto = jweService.generateAuthToken("example@example.com", 1L);
        Assertions.assertTrue(jweService.validateJweToken(jwtAuthenticationDto.token()));
    }

    /**
     * Проверяет обновление базового токена при сохранении того же refresh-токена.
     * <p>
     * Сценарий:
     * <ol>
     *     <li>Генерируется новый JWT-токен (включающий access и refresh токены) с помощью
     *     {@link JweService#generateAuthToken(String, Long)}.</li>
     *     <li>Выполняется пауза в 1 секунду, чтобы гарантировать различие во времени создания токенов.</li>
     *     <li>Обновляется токен с помощью {@link JweService#refreshBaseToken(String, String, Long)},
     *     передается старый refresh-токен.</li>
     * </ol>
     * <p>
     * Ожидается, что:
     * <ul>
     *     <li>Refresh-токен остается неизменным.</li>
     *     <li>Новый access-токен отличается от старого.</li>
     * </ul>
     *
     * @throws JOSEException если возникает ошибка при работе с JWT (например, подпись, расшифровка)
     * @throws InterruptedException если поток ожидания был прерван
     */
    @Test
    void refreshBaseToken() throws JOSEException, InterruptedException {
        JwtAuthDto jwtAuthenticationDto = jweService.generateAuthToken("example@example.com", 1L);

        Thread.sleep(1010);

        JwtAuthDto jwtAuthenticationDtoNew = jweService
                .refreshBaseToken("example@example.com", jwtAuthenticationDto.refreshToken(), 1L);

        Assertions.assertEquals(jwtAuthenticationDto.refreshToken(), jwtAuthenticationDtoNew.refreshToken());

        Assertions.assertNotEquals(jwtAuthenticationDtoNew.token(), jwtAuthenticationDto.token());
    }

    /**
     * Проверяет сценарий отрицательной валидации JWT-токена.
     * <p>
     * В тесте передается недействительный JWT-токен в метод {@link JweService#validateJweToken(String)}.
     * <p>
     * Ожидается, что метод вернет {@code false}, подтверждая, что токен не прошёл валидацию.
     */
    @Test
    void validateJwtTokenNegativeTest() {
        Assertions.assertFalse(jweService.validateJweToken("eyJjdHkiOiJKV1QiLCJlbmMiOiJBMjU2R0NNIiwiYWxnIjoiZGlyIn0.." +
                "Pyk2mfnP8Hvd9M8D.Anm8FY0EJvISkDvn1FdkUhVPpEWtQFsZV-oic8pddkH4PRxVwcDRYTFmTKezhThzZGbg3n-" +
                "l1Zrk26lMol_ilWJg3vpEuz6eYYaHac6ZVZVDWLJZ9ASuvGyvUZsN1vlZ2dYkp1kOKa6IrhbOQtuvrVAah5U8cgX6QQ." +
                "0cVJ1kd43nE6aAa5HKVfhA"));
    }
}