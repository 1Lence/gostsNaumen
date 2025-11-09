package com.example.gostsNaumen.service.user;

import com.example.gostsNaumen.entity.User;
import com.example.gostsNaumen.security.dto.JwtAuthDto;
import com.example.gostsNaumen.security.dto.RefreshTokenDto;
import com.example.gostsNaumen.security.jwe.JweService;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.naming.AuthenticationException;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private JweService jweService;

    @Mock
    private UserService userService;

    @InjectMocks
    private AuthService authService;

    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setEmail("test@example.com");
        user.setUsername("testuser");
        user.setPasswordHash("$2a$04$/d95NxhGEiMmDSJUb5jqG.WKNxDd1UpniGAljsbWxuSDyNh18bqj6");
    }

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

        AssertionsForClassTypes.assertThat(result).isEqualTo(refreshedJwt);
        Mockito.verify(jweService).validateJweToken("valid-refresh-token");
        Mockito.verify(jweService).getEmailFromToken("valid-refresh-token");
        Mockito.verify(jweService).refreshBaseToken("test@example.com", "valid-refresh-token", null);
    }

    @Test
    void refreshTokenShouldThrowAuthenticationExceptionWhenTokenInvalid() {
        RefreshTokenDto refreshTokenDto = new RefreshTokenDto("invalid-token");
        Mockito.when(jweService.validateJweToken("invalid-token")).thenReturn(false);

        Assertions.assertThatThrownBy(() -> authService.refreshToken(refreshTokenDto))
                .isInstanceOf(AuthenticationException.class)
                .hasMessage("Невалидный токен обновления.");
    }
}