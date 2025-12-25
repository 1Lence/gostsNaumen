package com.example.gostsNaumen.service.user;

import com.example.gostsNaumen.entity.User;
import com.example.gostsNaumen.security.dto.JwtAuthDto;
import com.example.gostsNaumen.security.dto.RefreshTokenDto;
import com.example.gostsNaumen.security.jwe.JweService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.naming.AuthenticationException;

/**
 * Тестирование логики сервиса авторизации
 */
@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    /**
     * Сервис для работы с JWE.
     * Используется для шифрования и расшифровки токенов.
     */
    private final JweService jweService;

    /**
     * Сервис для работы с пользователями.
     * Используется для поиска и управления данными пользователей.
     */
    private final UserService userService;

    /**
     * Основной сервис аутентификации, тестируемый в данном классе.
     * Обеспечивает логику входа, регистрации и обновления токенов.
     */
    private final AuthService authService;

    /**
     * Кодировщик паролей.
     * Используется для хеширования паролей и сравнения с сохраненными значениями.
     */
    private final PasswordEncoder passwordEncoder;

    private User user;

    public AuthServiceTest(
            @Mock JweService jweService,
            @Mock UserService userService
    ) {
        passwordEncoder = new BCryptPasswordEncoder(4);

        this.jweService = jweService;
        this.userService = userService;
        this.authService = new AuthService(
                jweService,
                userService,
                passwordEncoder
        );
    }

    /**
     * Подготовка сущности пользователя
     */
    @BeforeEach
    void setUp() {
        user = new User(
                "TestUser",
                "Petrov Petrov",
                "$2a$04$/d95NxhGEiMmDSJUb5jqG.WKNxDd1UpniGAljsbWxuSDyNh18bqj6",
                "test@example.com"
        );
    }

    /**
     * Проверяет, что метод {@link AuthService#refreshToken(RefreshTokenDto)}
     * корректно возвращает новые токены при предоставлении валидного токена обновления.
     * <h3>Тестируемый сценарий:</h3>
     * <ul>
     *      <li> 1. Предоставляется действительный токен обновления.</li>
     *      <li> 2. Токен проходит валидацию.</li>
     *      <li> 3. Извлекается email пользователя из токена.</li>
     *      <li> 4. Находится пользователь в системе по email.</li>
     *      <li> 5. Вызывается сервис генерации новых токенов.</li>
     *      <li> 6. Метод возвращает ожидаемый объект {@link JwtAuthDto} с новыми токенами.</li>
     * </ul>
     */
    @Test
    void refreshTokenShouldReturnNewTokenWhenValidRefreshTokenProvided() throws Exception {
        RefreshTokenDto refreshTokenDto = new RefreshTokenDto("valid-refresh-token");

        Mockito.when(jweService.validateJweToken("valid-refresh-token")).thenReturn(true);
        Mockito.when(userService.getEntityByEmail("test@example.com")).thenReturn(user);
        Mockito.when(jweService.getEmailFromToken("valid-refresh-token")).thenReturn("test@example.com");

        JwtAuthDto refreshedJwt = new JwtAuthDto(
                "new-access",
                "new-refresh",
                1L
        );

        Mockito.when(
                jweService.refreshBaseToken(
                        "test@example.com",
                        "valid-refresh-token", null)
        ).thenReturn(refreshedJwt);

        JwtAuthDto result = authService.refreshToken(refreshTokenDto);

        Assertions.assertEquals(result, refreshedJwt);
    }

    /**
     * Проверяет, что метод {@link AuthService#refreshToken(RefreshTokenDto)}
     * выбрасывает {@link AuthenticationException}, если предоставлен недействительный токен обновления.
     * <h3>Тестируемый сценарий:</h3>
     * <ul>
     *      <li> 1. Предоставляется недействительный токен обновления.</li>
     *      <li> 2. Валидация токена возвращает false.</li>
     *      <li> 3. Метод {@link AuthService#refreshToken} вызывает исключение.</li>
     *      <li> 4. Проверяется тип и сообщение исключения.</li>
     * </ul>
     */
    @Test
    void refreshTokenShouldThrowAuthenticationExceptionWhenTokenInvalid() {
        RefreshTokenDto refreshTokenDto = new RefreshTokenDto("invalid-token");
        Mockito.when(jweService.validateJweToken("invalid-token")).thenReturn(false);

        AuthenticationException exception = Assertions.assertThrows(
                AuthenticationException.class,
                () -> authService.refreshToken(refreshTokenDto)
        );

        Assertions.assertEquals("Невалидный токен обновления.", exception.getMessage());
    }
}