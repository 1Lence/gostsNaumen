package com.example.gostsNaumen.service.security;

import com.example.gostsNaumen.entity.User;
import com.example.gostsNaumen.exception.ErrorCode;
import com.example.gostsNaumen.security.dto.CustomUserDetails;
import com.example.gostsNaumen.security.permission.UserRoles;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

/**
 * Тестовый класс для {@link SecurityContextService}.
 * Проверяет корректность извлечения идентификатора аутентифицированного пользователя
 * из контекста безопасности Spring Security.
 */
@ExtendWith(MockitoExtension.class)
class SecurityContextServiceTest {

    /**
     * Тестируемый сервис для извлечения данных из контекста безопасности.
     */
    private final SecurityContextService securityContextService;

    /**
     * Мок-объект аутентификации Spring Security.
     * Используется для симуляции аутентифицированного пользователя.
     */
    private final Authentication authentication;

    /**
     * Мок-объект контекста безопасности Spring Security.
     * Используется для возврата объекта аутентификации.
     */
    private final SecurityContext securityContext;

    /**
     * Объект пользователя для тестирования.
     * Используется как сущность, связанная с аутентифицированным пользователем.
     */
    private User user;

    public SecurityContextServiceTest(
            @Mock Authentication authentication,
            @Mock SecurityContext securityContext
    ) {
        this.securityContext = securityContext;
        this.authentication = authentication;
        this.securityContextService = new SecurityContextService();
    }

    /**
     * Подготовка данных пользователя перед каждым тестом
     */
    @BeforeEach
    void stepUp() {
        user = new User(
                123L,
                "testNick",
                "FullName",
                "passwordHash",
                "test@email.com",
                UserRoles.USER
        );
    }

    /**
     * Проверяет, что метод {@link SecurityContextService#getLoggedInUserId()}
     * возвращает ID пользователя, если аутентификация прошла успешно
     * и principal является экземпляром {@link CustomUserDetails}.
     */
    @Test
    void getLoggedInUserIdShouldReturnUserIdWhenAuthenticated() {
        UserDetails customUserDetails = new CustomUserDetails(
                user,
                List.of(UserRoles.USER)
        );

        try (MockedStatic<SecurityContextHolder> mockedContextHolder = Mockito.mockStatic(SecurityContextHolder.class)) {
            mockedContextHolder.when(SecurityContextHolder::getContext).thenReturn(securityContext);

            Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
            Mockito.when(authentication.getPrincipal()).thenReturn(customUserDetails);
            Mockito.when(authentication.isAuthenticated()).thenReturn(true);

            Long actualUserId = securityContextService.getLoggedInUserId();
            Assertions.assertEquals(123L, actualUserId);
        }
    }

    /**
     * Проверяет, что метод {@link SecurityContextService#getLoggedInUserId()}
     * выбрасывает {@link BusinessException} с кодом {@link ErrorCode#INVALID_TOKEN},
     * если аутентификация отсутствует (authentication == null).
     */
    @Test
    void getLoggedInUserIdShouldThrowBusinessExceptionWhenAuthenticationIsNull() {
        try (MockedStatic<SecurityContextHolder> mockedContext = Mockito.mockStatic(SecurityContextHolder.class)) {
            mockedContext.when(SecurityContextHolder::getContext).thenReturn(securityContext);

            Mockito.when(securityContext.getAuthentication()).thenReturn(null);

            BusinessException exception = Assertions.assertThrows(
                    BusinessException.class,
                    securityContextService::getLoggedInUserId
            );

            Assertions.assertEquals(
                    ErrorCode.INVALID_TOKEN,
                    exception.getErrorCode()
            );

            Assertions.assertEquals(
                    "Невалидный токен",
                    exception.getFormattedMessage()
            );
        }
    }

    /**
     * Проверяет, что метод {@link SecurityContextService#getLoggedInUserId()}
     * выбрасывает {@link BusinessException} с кодом {@link ErrorCode#INVALID_TOKEN},
     * если аутентификация не прошла (isAuthenticated возвращает false).
     */
    @Test
    void getLoggedInUserIdShouldThrowBusinessExceptionWhenNotAuthenticated() {
        Mockito.when(authentication.isAuthenticated()).thenReturn(false);

        try (MockedStatic<SecurityContextHolder> mockedContext = Mockito.mockStatic(SecurityContextHolder.class)) {

            SecurityContext securityContext = Mockito.mock(SecurityContext.class);
            Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
            mockedContext.when(SecurityContextHolder::getContext).thenReturn(securityContext);

            BusinessException exception = Assertions.assertThrows(
                    BusinessException.class,
                    securityContextService::getLoggedInUserId
            );

            Assertions.assertEquals(ErrorCode.INVALID_TOKEN, exception.getErrorCode());

            Assertions.assertEquals(
                    "Невалидный токен",
                    exception.getFormattedMessage()
            );
        }
    }

    /**
     * Проверяет, что метод {@link SecurityContextService#getLoggedInUserId()}
     * выбрасывает {@link BusinessException} с кодом {@link ErrorCode#INVALID_TOKEN},
     * если principal не является экземпляром {@link CustomUserDetails}.
     */
    @Test
    void getLoggedInUserIdShouldThrowBusinessExceptionWhenPrincipalIsNotCustomUserDetails() {
        Mockito.when(authentication.getPrincipal()).thenReturn("SomeOtherPrincipal");
        Mockito.when(authentication.isAuthenticated()).thenReturn(true);

        try (MockedStatic<SecurityContextHolder> mockedContext = Mockito.mockStatic(SecurityContextHolder.class)) {
            SecurityContext securityContext = Mockito.mock(SecurityContext.class);
            Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
            mockedContext.when(SecurityContextHolder::getContext).thenReturn(securityContext);

            BusinessException exception = Assertions.assertThrows(
                    BusinessException.class,
                    securityContextService::getLoggedInUserId
            );

            Assertions.assertEquals(
                    ErrorCode.INVALID_TOKEN,
                    exception.getErrorCode()
            );

            Assertions.assertEquals(
                    "Невалидный токен",
                    exception.getFormattedMessage()
            );
        }
    }
}