package com.example.gostsNaumen.controller;

import com.example.gostsNaumen.controller.dto.UserMapper;
import com.example.gostsNaumen.controller.dto.request.UserDtoRequest;
import com.example.gostsNaumen.controller.dto.response.UserDtoResponse;
import com.example.gostsNaumen.entity.User;
import com.example.gostsNaumen.exception.BusinessException;
import com.example.gostsNaumen.security.dto.JwtAuthDto;
import com.example.gostsNaumen.security.dto.RefreshTokenDto;
import com.example.gostsNaumen.security.dto.UserCredentialsDto;
import com.example.gostsNaumen.service.user.AuthService;
import com.example.gostsNaumen.service.user.UserService;
import com.nimbusds.jose.JOSEException;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    private final Logger logger = LoggerFactory.getLogger(AuthController.class);
    private final AuthService authService;
    private final UserService userService;
    private final UserMapper userMapper;

    public AuthController(
            UserService userService,
            UserMapper userMapper,
            AuthService authService
    ) {
        this.userMapper = userMapper;
        this.userService = userService;
        this.authService = authService;
    }

    /**
     * Эндпоинт для логина.
     *
     * @param userCredentialsDto данные пользователя, достаточные для входа (почта, пароль).
     * @return Дто с токенами и айди пользователя.
     */
    @PostMapping("/sign-in")
    public JwtAuthDto signIn(@RequestBody @Valid UserCredentialsDto userCredentialsDto) {
        try {
            return authService.signIn(userCredentialsDto);
        } catch (AuthenticationException | JOSEException e) {
            logger.error("Authentication failed: {}", e.getMessage(), e);
            throw new RuntimeException("Auth failed: " + e.getMessage(), e);
        } catch (BusinessException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Эндпоинт для обновления основного токена. Используется на фронтенде.
     *
     * @param refreshTokenDto токен обновления
     * @return Дто с токенами и айди пользователя.
     * @throws AuthenticationException какие-либо проблемы с аутентификацией.
     * @throws JOSEException           общая ошибка работы с JWT, проблемы с подписями, шифрованием.
     */
    @PostMapping("/refresh")
    public JwtAuthDto refresh(
            @RequestBody RefreshTokenDto refreshTokenDto
    ) throws AuthenticationException, JOSEException, BusinessException {
        return authService.refreshToken(refreshTokenDto);
    }

    /**
     * Эндпоинт для регистрации.
     *
     * @param userDtoRequest Дто с данными пользователя достаточными для входа
     * @return Сущность записанную в бд.
     */
    @PostMapping("/registration")
    public ResponseEntity<UserDtoResponse> register(@RequestBody @Valid UserDtoRequest userDtoRequest) throws BusinessException {
        User user = userMapper.mapToEntity(userDtoRequest);
        UserDtoResponse userDtoResponse = userMapper.mapEntityToDto(userService.saveUser(user));
        return new ResponseEntity<>(userDtoResponse, HttpStatus.CREATED);
    }
}