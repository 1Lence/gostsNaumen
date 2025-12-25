package com.example.gostsNaumen.controller;

import com.example.gostsNaumen.controller.dto.UserMapper;
import com.example.gostsNaumen.controller.dto.request.PasswordDtoRequest;
import com.example.gostsNaumen.controller.dto.request.UpdateUserDtoRequest;
import com.example.gostsNaumen.controller.dto.response.UserDtoResponse;
import com.example.gostsNaumen.controller.dto.response.UserIdDtoResponse;
import com.example.gostsNaumen.entity.User;
import com.example.gostsNaumen.service.user.UserService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Контроллер по работе с пользовательских данных.
 */
@RestController
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;
    private final UserMapper userMapper;

    public UserController(
            UserService userService,
            UserMapper userMapper
    ) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    /**
     * Получение списка всех пользователей.
     *
     * @return список пользователей.
     */
    @GetMapping("/users")
    @PreAuthorize("hasAuthority('user:write')")
    @Transactional
    public List<UserDtoResponse> getAllUsers() {
        List<User> users = userService.findAll();

        return users.stream().map(userMapper::mapEntityToDto).toList();
    }

    /**
     * Обновить данные пользователя в БД.
     *
     * @param id айди пользователя в БД.
     * @return дто с обновленной сущностью пользователя.
     */
    @PatchMapping("/{id}")
    @PreAuthorize("hasAuthority('user:write')")
    @Transactional
    public UserDtoResponse updateUserData(
            @PathVariable Long id,
            @Valid @RequestBody UpdateUserDtoRequest updateUserDtoRequest
    ) {
        User user = userService.updateUserData(id, updateUserDtoRequest);

        return userMapper.mapEntityToDto(user);
    }

    /**
     * Обновление пароля пользователя.
     * Необходимо помнить, что никто кроме админа не должен иметь возможности изменить пароль другого пользователя.
     *
     * @param passwordDtoRequest дто с новым паролем
     * @return айди пользователя у которого обновился пароль
     */
    @PatchMapping("/password")
    @PreAuthorize("hasAuthority('user:read')")
    @Transactional
    public UserIdDtoResponse updatePassword(
            @RequestBody @Valid PasswordDtoRequest passwordDtoRequest
    ) {
        Long userId = userService.updatePassword(passwordDtoRequest);

        return new UserIdDtoResponse(userId);
    }

    /**
     * Эндпоинт для удаления пользователя по id.
     *
     * @param id айди пользователя из БД.
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('user:write')")
    @Transactional
    public ResponseEntity<Map<String, String>> deleteUserById(@PathVariable Long id) {
        String username = userService.deleteUserById(id);

        return ResponseEntity.ok(Map.of(
                "message", "Пользователь успешно удалён",
                "deletedUsername", username
        ));
    }
}