package com.example.gostsNaumen.controller.security;

import com.example.gostsNaumen.dto.request.UserDtoRequest;
import com.example.gostsNaumen.dto.response.UserDtoResponse;
import com.example.gostsNaumen.security.dto.JwtAuthDto;
import com.example.gostsNaumen.security.dto.RefreshTokenDto;
import com.example.gostsNaumen.security.dto.UserCredentialsDto;
import com.example.gostsNaumen.service.user.UserService;
import com.nimbusds.jose.JOSEException;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.naming.AuthenticationException;

/**
 * Контролер регистрации и авторизации.
 */
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Эндпоинт для логина.
     * @param userCredentialsDto данные пользователя, достаточные для входа (почта, пароль).
     * @return Дто с токенами и айди пользователя.
     */
    @PostMapping("/sign-in")
    public JwtAuthDto signIn(@RequestBody @Valid UserCredentialsDto userCredentialsDto) {
        try {
            return userService.signIn(userCredentialsDto);
        }catch (AuthenticationException | JOSEException e) {
            throw new RuntimeException("Auth failed: " + e.getMessage());
        }
    }

    /**
     * Эндпоинт для обновления основного токена. Используется на фронтенде.
     * @param refreshTokenDto токен обновления
     * @return Дто с токенами и айди пользователя.
     * @throws AuthenticationException какие-либо проблемы с аутентификацией.
     * @throws JOSEException общая ошибка работы с JWT, проблемы с подписями, шифрованием.
     */
    @PostMapping("/refresh")
    public JwtAuthDto refresh(@RequestBody RefreshTokenDto refreshTokenDto) throws AuthenticationException, JOSEException {
        return userService.refreshToken(refreshTokenDto);
    }

    /**
     * Эндпоинт для регистрации.
     * @param userDtoRequest Дто с данными пользователя достаточными для входа
     * @return Сущность записанную в бд.
     */
    @PostMapping("/registration")
    public UserDtoResponse register(@RequestBody @Valid UserDtoRequest userDtoRequest) {
        return userService.saveUser(userDtoRequest);
    }
}