package com.example.gostsNaumen.service.user;

import com.example.gostsNaumen.controller.dto.request.PasswordDtoRequest;
import com.example.gostsNaumen.controller.dto.request.UpdateUserDtoRequest;
import com.example.gostsNaumen.entity.User;
import com.example.gostsNaumen.exception.EntityExistsException;
import com.example.gostsNaumen.exception.EntityNotFoundException;
import com.example.gostsNaumen.repository.UserRepository;
import com.example.gostsNaumen.security.permission.UserRoles;
import com.example.gostsNaumen.service.security.SecurityContextService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

/**
 * Сервис по работе с пользовательскими данными.
 */
@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final SecurityContextService securityContextService;

    public UserService(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            SecurityContextService securityContextService
    ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.securityContextService = securityContextService;
    }

    /**
     * Сохранить сущность пользователя в БД.
     *
     * @param user сущность пригодная для сохранения.
     * @return сохранённая сущность в БД.
     */
    public User saveUser(User user) {
        if (userRepository.findUserByEmail(user.getEmail()).isPresent()) {
            throw new EntityExistsException(
                    String.format("Пользователь с почтой %s, уже существует", user.getEmail())
            );
        } else if (userRepository.findUserByUsername(user.getUsername()).isPresent()) {
            throw new EntityExistsException(
                    String.format("Пользователь с ником %s, уже существует", user.getUsername())
            );
        }

        return userRepository.save(user);
    }

    /**
     * Поиск сущности пользователя в пределах пакета по почте.
     *
     * <p>В случае, когда не существует пользователя по указанному {@code email}
     * выбрасывается исключение {@code BusinessException }</p>
     *
     * @param email почта пользователя.
     * @return сущность пользователя из БД.
     */
    public User findEntityByEmail(String email) {
        return userRepository.findUserByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format("Пользователя по почте: %s не существует", email)));
    }

    /**
     * Поиск сущности пользователя в пределах пакета по айди.
     *
     * <p>В случае, когда не существует пользователя по указанному {@code id}
     * выбрасывается исключение {@code BusinessException }</p>
     *
     * @param id айди пользователя
     * @return сущность пользователя из БД.
     */
    public User findEntityById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(
                String.format("Пользователя по ID: %s не существует", id))
        );
    }

    /**
     * Обновляет пользователя по введенным данным.
     * Производит проверку на наличие уже существующих записей по уникальным полям.
     *
     * @param id                   айди пользователя
     * @param updateUserDtoRequest дто с данными пользователя
     * @return обновленная сущность пользователя
     */
    public User updateUserData(Long id, UpdateUserDtoRequest updateUserDtoRequest) {
        User user = findEntityById(id);

        if (updateUserDtoRequest.userName() != null && !updateUserDtoRequest.userName().isEmpty()) {
            userRepository.findUserByUsername(updateUserDtoRequest.userName())
                    .ifPresent(u -> {
                        throw new EntityExistsException(
                                String.format("Пользователь с ником: %s уже существует", u.getUsername()));
                    });

            user.setUsername(updateUserDtoRequest.userName());
        }
        if (updateUserDtoRequest.email() != null && !updateUserDtoRequest.email().isEmpty()) {
            userRepository.findUserByEmail(updateUserDtoRequest.email())
                    .ifPresent(u -> {
                        throw new EntityExistsException(
                                String.format("Пользователь с почтой: %s уже существует", u.getEmail()));
                    });

            user.setEmail(updateUserDtoRequest.email());
        }
        if (updateUserDtoRequest.fullName() != null && !updateUserDtoRequest.fullName().isEmpty()) {
            user.setFullName(updateUserDtoRequest.fullName());
        }
        if (updateUserDtoRequest.role() != null && !updateUserDtoRequest.role().isEmpty()) {
            user.setRoles(UserRoles.valueOf(updateUserDtoRequest.role()));
        }

        return user;
    }

    /**
     * Обновление пароля пользователя.
     * Пользователь не должен иметь возможности обновить пароль другого пользователя
     *
     * @param newPassword ДТО с новым паролем пользователя
     */
    public Long updatePassword(PasswordDtoRequest newPassword) {
        Long userId = securityContextService.getLoggedInUserId();

        User user = findEntityById(userId);

        user.setPasswordHash(passwordEncoder.encode(newPassword.newPassword()));

        return user.getId();
    }

    /**
     * Выводит список всех пользователей.
     * Доступен только для админа.
     *
     * @return список пользователей
     */
    public List<User> findAll() {
        return Collections.unmodifiableList(userRepository.findAll());
    }

    /**
     * Удаляет пользователя по Id, но перед этим происходит проверка, существует ли пользователь с таким id в методе {@link #findEntityById(Long)}
     *
     * @param id id пользователя в БД
     */
    public void deleteUserById(Long id) {
        findEntityById(id);

        userRepository.deleteById(id);
    }
}