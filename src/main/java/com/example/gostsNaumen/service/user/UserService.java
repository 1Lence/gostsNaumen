package com.example.gostsNaumen.service.user;

import com.example.gostsNaumen.controller.dto.request.PasswordDtoRequest;
import com.example.gostsNaumen.controller.dto.request.UpdateUserDtoRequest;
import com.example.gostsNaumen.entity.User;
import com.example.gostsNaumen.repository.UserRepository;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Сервис по работе с пользовательскими данными.
 */
@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }
    //TODO: Добавить все операции с пользователем,

    /**
     * Сохранить сущность пользователя в БД.
     *
     * @param user сущность пригодная для сохранения.
     * @return сохранённая сущность в БД.
     */
    @Transactional
    public User saveUser(User user) {
        if (userRepository.findUserByEmail(user.getEmail()).isPresent()) {
            throw new EntityExistsException("Пользователь с почтой: " + user.getEmail() + " уже существует.");
        } else if (userRepository.findUserByUsername(user.getUsername()).isPresent()) {
            throw new EntityExistsException("Пользователь с ником: " + user.getUsername() + " уже существует.");
        }

        return userRepository.save(user);
    }

    /**
     * Поиск сущности пользователя в пределах пакета по почте.
     *
     * @param email почта пользователя.
     * @return сущность пользователя из БД.
     */
    @Transactional
    public User findEntityByEmail(String email) {
        return userRepository.findUserByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("There is not User with email: " + email));
    }

    /**
     * Поиск сущности пользователя в пределах пакета по айди.
     *
     * @param id айди пользователя
     * @return сущность пользователя из БД.
     */
    @Transactional
    public User findEntityById(Long id) {
        return userRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("There is no User with id: " + id)
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
    @Transactional
    public User updateUserData(Long id, UpdateUserDtoRequest updateUserDtoRequest) {
        User user = findEntityById(id);

        if (userRepository.findUserByEmail(user.getEmail()).isPresent()) {
            throw new EntityExistsException("Пользователь с почтой: " + user.getEmail() + " уже существует.");
        } else if (userRepository.findUserByUsername(user.getUsername()).isPresent()) {
            throw new EntityExistsException("Пользователь с ником: " + user.getUsername() + " уже существует.");
        }

        user.setUsername(updateUserDtoRequest.userName());
        user.setEmail(updateUserDtoRequest.email());
        user.setFullName(updateUserDtoRequest.fullName());
        user.setRoles(updateUserDtoRequest.role());

        return user;
    }

    /**
     * Обновление пароля пользователя.
     * Пользователь не должен иметь возможности обновить пароль другого пользователя
     *
     * @param id          айди залогиненного пользователя
     * @param newPassword ДТО с новым паролем пользователя
     */
    @Transactional
    public Long updatePassword(Long id, PasswordDtoRequest newPassword) {
        User user = findEntityById(id);

        user.setPasswordHash(passwordEncoder.encode(newPassword.newPassword()));

        return user.getId();
    }

    /**
     * Выводит список всех пользователей.
     * Доступен только для админа.
     *
     * @return список пользователей
     */
    @Transactional(readOnly = true)
    public List<User> findAll() {
        return userRepository.findAll();
    }

    /**
     * Удаляет пользователя по Id, но перед этим происходит проверка, существует ли пользователь с таким id
     *
     * @param id id пользователя в БД
     */
    @Transactional
    public void deleteUserById(Long id) {
        findEntityById(id);

        userRepository.deleteById(id);
    }
}