package com.example.gostsNaumen.service.user;

import com.example.gostsNaumen.entity.User;
import com.example.gostsNaumen.repository.UserRepository;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Сервис по работе с пользовательскими данными.
 */
@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

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
    public User findEntityById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("There is no User with id: " + id));
    }
}