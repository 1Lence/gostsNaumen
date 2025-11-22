package com.example.gostsNaumen.service.user;

import com.example.gostsNaumen.controller.dto.request.UpdateUserDtoRequest;
import com.example.gostsNaumen.entity.User;
import com.example.gostsNaumen.exception.BusinessException;
import com.example.gostsNaumen.exception.ErrorCode;
import com.example.gostsNaumen.repository.UserRepository;
import com.example.gostsNaumen.security.permission.UserRoles;
import com.example.gostsNaumen.service.security.SecurityContextService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

/**
 * Проверяет бизнес-логику, связанную с управлением пользователями,
 * включая создание, обновление, удаление и поиск пользователей.
 */
@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    /**
     * Репозиторий для работы с сущностями пользователей.
     * Используется для взаимодействия с базой данных.
     */
    private final UserRepository userRepository;

    /**
     * Кодировщик паролей.
     * Применяется для хеширования паролей при создании или обновлении пользователей.
     */
    private final PasswordEncoder passwordEncoder;

    /**
     * Сервис для работы с контекстом безопасности.
     * Используется для получения информации о текущем аутентифицированном пользователе.
     */
    private final SecurityContextService securityContextService;

    /**
     * Основной сервис пользователей, тестируемый в данном классе.
     * Содержит бизнес-методы для работы с пользователями.
     */
    private final UserService userService;

    /**
     * Объект пользователя для тестирования.
     * Используется как сущность, связанная с аутентифицированным пользователем.
     */
    private User user;

    public UserServiceTest(
            @Mock UserRepository userRepository,
            @Mock SecurityContextService securityContextService
    ) {
        this.userRepository = userRepository;
        this.securityContextService = securityContextService;
        this.passwordEncoder = new BCryptPasswordEncoder(4);

        this.userService = new UserService(
                userRepository,
                passwordEncoder,
                securityContextService
        );
    }

    /**
     * Подготовка данных пользователя перед каждым тестом
     */
    @BeforeEach
    void stepUp() {
        user = new User(
                "TestUser",
                "Petrov Petrov",
                "$2a$04$/d95NxhGEiMmDSJUb5jqG.WKNxDd1UpniGAljsbWxuSDyNh18bqj6",
                "test@example.com",
                UserRoles.USER
        );
    }

    /**
     * Проверяет, что метод {@link UserService#saveUser(User)}
     * выбрасывает {@link BusinessException} с кодом
     * {@code ErrorCode#USER_FIELDS_ALREADY_EXIST} и корректным сообщением,
     * если пользователь с такой почтой уже существует в БД.
     */
    @Test
    void saveUserShouldThrowBusinessExceptionWhenUserWithCurrenEmailPresent() {
        Mockito.when(userRepository.findUserByEmail(user.getEmail())).thenReturn(Optional.of(user));

        BusinessException exception = Assertions.assertThrows(
                BusinessException.class,
                () -> userService.saveUser(user)
        );

        Assertions.assertEquals(
                ErrorCode.USER_FIELDS_ALREADY_EXIST,
                exception.getErrorCode()
        );

        Assertions.assertEquals(
                "Пользователь с почтой test@example.com, уже существует",
                exception.getFormattedMessage()
        );
    }

    /**
     * Проверяет, что метод {@link UserService#saveUser(User)}
     * выбрасывает {@link BusinessException} с кодом
     * {@code ErrorCode.USER_FIELDS_ALREADY_EXIST} и корректным сообщением,
     * если пользователь с таким {@code username} уже существует в БД.
     * Также проверяется, что были вызваны методы {@link UserRepository#findUserByEmail(String)}
     * и {@link UserRepository#findUserByUsername(String)} по одному разу.
     */
    @Test
    void saveUserShouldThrowBusinessExceptionWhenUserWithCurrentUserNamePresent() {
        Mockito.when(userRepository.findUserByEmail(user.getEmail())).thenReturn(Optional.empty());
        Mockito.when(userRepository.findUserByUsername(user.getUsername())).thenReturn(Optional.of(user));

        BusinessException exception = Assertions.assertThrows(
                BusinessException.class,
                () -> userService.saveUser(user)
        );

        Assertions.assertEquals(
                "Пользователь с ником TestUser, уже существует",
                exception.getFormattedMessage()
        );

        Assertions.assertEquals(
                ErrorCode.USER_FIELDS_ALREADY_EXIST,
                exception.getErrorCode()
        );

        Mockito.verify(
                userRepository,
                Mockito.times(1)
        ).findUserByEmail(user.getEmail());

        Mockito.verify(
                userRepository,
                Mockito.times(1)
        ).findUserByUsername(user.getUsername());
    }

    /**
     * Проверяет, что метод {@link UserService#findEntityByEmail(String)}
     * выбрасывает {@link BusinessException} с кодом
     * {@code ErrorCode.USER_NOT_FOUND} и корректным сообщением,
     * если пользователь с указанной электронной почтой не в БД.
     */
    @Test
    void findEntityByEmailShouldThrowBusinessExceptionWhenUserWithCurrenEmailNotPresent() {
        Mockito.when(userRepository.findUserByEmail("test@example.com")).thenReturn(Optional.empty());

        BusinessException exception = Assertions.assertThrows(
                BusinessException.class,
                () -> userService.findEntityByEmail("test@example.com")
        );

        Assertions.assertEquals(
                ErrorCode.USER_NOT_FOUND,
                exception.getErrorCode()
        );

        Assertions.assertEquals(
                "Пользователя по почте: test@example.com не существует",
                exception.getFormattedMessage()
        );
    }

    /**
     * Проверяет, что метод {@link UserService#findEntityById(Long)}
     * выбрасывает {@link BusinessException} с кодом
     * {@code ErrorCode.USER_NOT_FOUND} и корректным сообщением,
     * если пользователь с указанным идентификатором не БД.
     */
    @Test
    void findEntityByIdShouldThrowBusinessExceptionWhenUserWithCurrenIdNotPresent() {
        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.empty());

        BusinessException exception = Assertions.assertThrows(
                BusinessException.class,
                () -> userService.findEntityById(1L)
        );

        Assertions.assertEquals(
                ErrorCode.USER_NOT_FOUND,
                exception.getErrorCode()
        );

        Assertions.assertEquals(
                "Пользователя по ID: 1 не существует",
                exception.getFormattedMessage()
        );
    }

    /**
     * Проверяет, что метод {@link UserService#updateUserData(Long, UpdateUserDtoRequest)}
     * корректно обновляет имя пользователя, если оно передано и не является пустым.
     * Проверяется, что {@link UserRepository#findUserByUsername(String)} вызывается один раз,
     * и что имя пользователя в сущности обновляется.
     */
    @Test
    void updateUserDataShouldUpdateUsernameWhenValidUsernameProvided() {
        Long userId = 1L;
        String newUsername = "newUsername";
        UpdateUserDtoRequest request = new UpdateUserDtoRequest(
                newUsername, null, null, null
        );

        Mockito.when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        Mockito.when(userRepository.findUserByUsername(newUsername)).thenReturn(Optional.empty());

        User updatedUser = userService.updateUserData(userId, request);

        Assertions.assertEquals(newUsername, updatedUser.getUsername());
        Mockito.verify(userRepository, Mockito.times(1)).findUserByUsername(newUsername);
    }

    /**
     * Проверяет, что метод {@link UserService#updateUserData(Long, UpdateUserDtoRequest)}
     * выбрасывает {@link BusinessException}, если имя пользователя, которое нужно обновить,
     * уже занято другим пользователем.
     */
    @Test
    void updateUserDataShouldThrowBusinessExceptionWhenUsernameAlreadyExists() {
        UpdateUserDtoRequest request = new UpdateUserDtoRequest(
                "existingUsername", null, null, null
        );

        User conflictingUser = new User(
                "existingUsername",
                "Conflicting Name",
                "passwordHash",
                "conflict@example.com",
                UserRoles.USER
        );

        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        Mockito.when(userRepository.findUserByUsername("existingUsername")).thenReturn(Optional.of(conflictingUser));

        BusinessException exception = Assertions.assertThrows(
                BusinessException.class,
                () -> userService.updateUserData(1L, request)
        );

        Assertions.assertEquals(
                ErrorCode.USER_FIELDS_ALREADY_EXIST,
                exception.getErrorCode()
        );

        Assertions.assertEquals(
                "Пользователь с ником: existingUsername уже существует",
                exception.getFormattedMessage()
        );
    }

    /**
     * Проверяет, что метод {@link UserService#updateUserData(Long, UpdateUserDtoRequest)}
     * корректно обновляет email пользователя, если он передан и не является пустым.
     * Проверяется, что {@link UserRepository#findUserByEmail(String)} вызывается один раз,
     * и что email пользователя в сущности обновляется.
     */
    @Test
    void updateUserDataShouldUpdateEmailWhenValidEmailProvided() {
        UpdateUserDtoRequest request = new UpdateUserDtoRequest(
                null, null, "new@example.com", null
        );

        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        Mockito.when(userRepository.findUserByEmail("new@example.com")).thenReturn(Optional.empty());

        User updatedUser = userService.updateUserData(1L, request);

        Assertions.assertEquals("new@example.com", updatedUser.getEmail());
    }

    /**
     * Проверяет, что метод {@link UserService#updateUserData(Long, UpdateUserDtoRequest)}
     * выбрасывает {@link BusinessException}, если email, который нужно обновить,
     * уже занят другим пользователем.
     */
    @Test
    void updateUserDataShouldThrowBusinessExceptionWhenEmailAlreadyExists() {
        UpdateUserDtoRequest request = new UpdateUserDtoRequest(
                null, null, "existing@example.com", null
        );

        User conflictingUser = new User(
                "anotherUsername",
                "Conflicting Name",
                "passwordHash",
                "existing@example.com",
                UserRoles.USER
        );

        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        Mockito.when(userRepository.findUserByEmail("existing@example.com")).thenReturn(Optional.of(conflictingUser));

        BusinessException exception = Assertions.assertThrows(
                BusinessException.class,
                () -> userService.updateUserData(1L, request)
        );

        Assertions.assertEquals(
                ErrorCode.USER_FIELDS_ALREADY_EXIST,
                exception.getErrorCode()
        );

        Assertions.assertEquals(
                "Пользователь с почтой: existing@example.com уже существует",
                exception.getFormattedMessage()
        );
    }

    /**
     * Проверяет, что метод {@link UserService#updateUserData(Long, UpdateUserDtoRequest)}
     * корректно обновляет полное имя пользователя, если оно передано и не является пустым.
     */
    @Test
    void updateUserDataShouldUpdateFullNameWhenValidFullNameProvided() {
        UpdateUserDtoRequest request = new UpdateUserDtoRequest(
                null, "New Full Name", null, null
        );

        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        User updatedUser = userService.updateUserData(1L, request);

        Assertions.assertEquals("New Full Name", updatedUser.getFullName());
    }

    /**
     * Проверяет, что метод {@link UserService#updateUserData(Long, UpdateUserDtoRequest)}
     * корректно обновляет роль пользователя, если она передана и не является пустой.
     */
    @Test
    void updateUserDataShouldUpdateRoleWhenValidRoleProvided() {
        UpdateUserDtoRequest request = new UpdateUserDtoRequest(
                null, null, null, "ADMIN"
        );

        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        User updatedUser = userService.updateUserData(1L, request);

        Assertions.assertEquals(UserRoles.ADMIN, updatedUser.getRoles());
    }

    /**
     * Проверяет, что метод {@link UserService#updateUserData(Long, UpdateUserDtoRequest)}
     * корректно обновляет только те поля, которые переданы в DTO.
     * Поля, равные null или пустые строки, не изменяются.
     */
    @Test
    void updateUserDataShouldUpdateOnlyNonNullAndNonEmptyFields() {
        UpdateUserDtoRequest request = new UpdateUserDtoRequest(
                "newUsername", "", null, null
        );

        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        Mockito.when(userRepository.findUserByUsername("newUsername")).thenReturn(Optional.empty());

        User updatedUser = userService.updateUserData(1L, request);

        Assertions.assertEquals("newUsername", updatedUser.getUsername());
        Assertions.assertEquals("test@example.com", updatedUser.getEmail());
        Assertions.assertEquals("Petrov Petrov", updatedUser.getFullName());
        Assertions.assertEquals(UserRoles.USER, updatedUser.getRoles());
    }
}