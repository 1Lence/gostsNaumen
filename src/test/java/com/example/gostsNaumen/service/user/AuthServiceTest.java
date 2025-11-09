package com.example.gostsNaumen.service.user;

import com.example.gostsNaumen.entity.User;
import com.example.gostsNaumen.security.dto.JwtAuthDto;
import com.example.gostsNaumen.security.dto.RefreshTokenDto;
import com.example.gostsNaumen.security.jwe.JweService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.naming.AuthenticationException;

/**
 * Тестирование логики сервиса авторизации
 */
@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private JweService jweService;

    @Mock
    private UserService userService;

    @InjectMocks
    private AuthService authService;

    private User user;

    /**
     * Подготовка сущности пользователя
     */
    @BeforeEach
    void setUp() {
        user = new User();
        user.setEmail("test@example.com");
        user.setUsername("testuser");
        user.setPasswordHash("$2a$04$/d95NxhGEiMmDSJUb5jqG.WKNxDd1UpniGAljsbWxuSDyNh18bqj6");
    }

    /**
     * Проверяет работу метода по обновлению токена доступа с помощью токена обновления.
     *
     * @throws Exception
     */
    @Test
    void refreshTokenShouldReturnNewTokenWhenValidRefreshTokenProvided() throws Exception {
        RefreshTokenDto refreshTokenDto = new RefreshTokenDto("valid-refresh-token");

        Mockito.when(jweService.validateJweToken("valid-refresh-token")).thenReturn(true);
        Mockito.when(userService.findEntityByEmail("test@example.com")).thenReturn(user);
        Mockito.when(jweService.getEmailFromToken("valid-refresh-token")).thenReturn("test@example.com");

        JwtAuthDto refreshedJwt = new JwtAuthDto(
                "new-access",
                "new-refresh",
                1L
        );

        Mockito.when(jweService.refreshBaseToken("test@example.com", "valid-refresh-token", null)).thenReturn(refreshedJwt);

        JwtAuthDto result = authService.refreshToken(refreshTokenDto);

        Assertions.assertThat(result).isEqualTo(refreshedJwt);
    }

    /**
     * Проверяет, что в случае невалидного токена обновления выбросится ошибка и не будет обновлен токен доступа
     */
    @Test
    void refreshTokenShouldThrowAuthenticationExceptionWhenTokenInvalid() {
        RefreshTokenDto refreshTokenDto = new RefreshTokenDto("invalid-token");
        Mockito.when(jweService.validateJweToken("invalid-token")).thenReturn(false);

        Assertions.assertThatThrownBy(() -> authService.refreshToken(refreshTokenDto))
                .isInstanceOf(AuthenticationException.class)
                .hasMessage("Невалидный токен обновления.");
    }
}