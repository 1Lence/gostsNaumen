package com.example.gostsNaumen.service.user;

import com.example.gostsNaumen.entity.User;
import com.example.gostsNaumen.repository.UserRepository;
import com.example.gostsNaumen.security.dto.JwtAuthDto;
import com.example.gostsNaumen.security.dto.RefreshTokenDto;
import com.example.gostsNaumen.security.dto.UserCredentialsDto;
import com.example.gostsNaumen.security.jwe.JweService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.naming.AuthenticationException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private JweService jweService;

    @InjectMocks
    private UserService userService;

    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setEmail("test@example.com");
        user.setUsername("testuser");
        user.setPasswordHash("$2a$04$/d95NxhGEiMmDSJUb5jqG.WKNxDd1UpniGAljsbWxuSDyNh18bqj6");
    }

    @Test
    void signInShouldThrowEntityNotFoundExceptionWhenUserNotFound() {
        when(userRepository.findUserByEmail("nonexistent@example.com")).thenReturn(Optional.empty());

        UserCredentialsDto dto = new UserCredentialsDto("nonexistent@example.com", "password");

        assertThatThrownBy(() -> userService.signIn(dto))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("Пользователь с почтой: nonexistent@example.com не найден.");
    }

    @Test
    void refreshTokenShouldReturnNewTokenWhenValidRefreshTokenProvided() throws Exception {
        RefreshTokenDto refreshTokenDto = new RefreshTokenDto("valid-refresh-token");

        when(jweService.validateJweToken("valid-refresh-token")).thenReturn(true);
        when(jweService.getEmailFromToken("valid-refresh-token")).thenReturn("test@example.com");
        when(userRepository.findUserByEmail("test@example.com")).thenReturn(Optional.of(user));

        JwtAuthDto refreshedJwt = new JwtAuthDto(
                "new-access",
                "new-refresh",
                1L
        );

        when(jweService.refreshBaseToken("test@example.com", "valid-refresh-token", null)).thenReturn(refreshedJwt);

        JwtAuthDto result = userService.refreshToken(refreshTokenDto);

        assertThat(result).isEqualTo(refreshedJwt);
        verify(jweService).validateJweToken("valid-refresh-token");
        verify(jweService).getEmailFromToken("valid-refresh-token");
        verify(userRepository).findUserByEmail("test@example.com");
        verify(jweService).refreshBaseToken("test@example.com", "valid-refresh-token", null);
    }

    @Test
    void refreshTokenShouldThrowAuthenticationExceptionWhenTokenInvalid() {
        RefreshTokenDto refreshTokenDto = new RefreshTokenDto("invalid-token");
        when(jweService.validateJweToken("invalid-token")).thenReturn(false);

        assertThatThrownBy(() -> userService.refreshToken(refreshTokenDto))
                .isInstanceOf(AuthenticationException.class)
                .hasMessage("Невалидный токен обновления.");
    }
}