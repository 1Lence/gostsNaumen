package com.example.gostsNaumen.service.user;

import com.example.gostsNaumen.entity.User;
import com.example.gostsNaumen.security.dto.JwtAuthDto;
import com.example.gostsNaumen.security.dto.RefreshTokenDto;
import com.example.gostsNaumen.security.dto.UserCredentialsDto;
import com.example.gostsNaumen.security.jwe.JweService;
import com.nimbusds.jose.JOSEException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.naming.AuthenticationException;

/**
 * Реализует логику авторизации пользователей
 */
@Service
public class AuthService {
    private final JweService jweService;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public AuthService(JweService jweService, UserService userService, PasswordEncoder passwordEncoder) {
        this.jweService = jweService;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }
    //TODO: Добавить LogOut
    /**
     * Вход в систему.
     * Сверка данных из БД, с введенными данными проходит в методе
     * {@link #checkUserCredentials(UserCredentialsDto userCredentialsDto)}
     *
     * @param userCredentialsDto данные введённые пользователями.
     * @return дто с токенами и айди пользователя.
     * @throws AuthenticationException какие-либо проблемы с аутентификацией.
     * @throws JOSEException           общая ошибка работы с JWT, проблемы с подписями, шифрованием.
     * @see #checkUserCredentials
     */
    public JwtAuthDto signIn(UserCredentialsDto userCredentialsDto) throws AuthenticationException, JOSEException {
        User user = checkUserCredentials(userCredentialsDto);
        return jweService.generateAuthToken(user.getEmail(), user.getId());
    }

    /**
     * Обновление основного токена пользователя.
     *
     * @param refreshTokenDto токен обновления.
     * @return дто с токенами и айди пользователя.
     * @throws AuthenticationException какие-либо проблемы с аутентификацией.
     * @throws JOSEException           общая ошибка работы с JWT, проблемы с подписями, шифрованием.
     */
    public JwtAuthDto refreshToken(RefreshTokenDto refreshTokenDto) throws AuthenticationException, JOSEException {
        String refreshedToken = refreshTokenDto.refreshToken();

        if (refreshedToken != null && jweService.validateJweToken(refreshedToken)) {
            User user = userService.findEntityByEmail(jweService.getEmailFromToken(refreshedToken));
            return jweService.refreshBaseToken(user.getEmail(), refreshedToken, user.getId());
        }
        throw new AuthenticationException("Невалидный токен обновления.");
    }

    /**
     * Сверка данных пользователя введенных при входе.
     *
     * @param userCredentialsDto данные пользователя.
     * @return сущность пользователя из БД.
     * @throws AuthenticationException ошибка сверки данных.
     */
    private User checkUserCredentials(UserCredentialsDto userCredentialsDto) throws AuthenticationException {
        User user = userService.findEntityByEmail(userCredentialsDto.email());

        if (passwordEncoder.matches(userCredentialsDto.password(), user.getPasswordHash())) {
            return user;
        } else {
            throw new AuthenticationException("Почта или пароль неверные.");
        }
    }
}