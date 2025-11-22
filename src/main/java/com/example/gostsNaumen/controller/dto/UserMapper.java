package com.example.gostsNaumen.controller.dto;

import com.example.gostsNaumen.controller.dto.request.UserDtoRequest;
import com.example.gostsNaumen.controller.dto.response.UserDtoResponse;
import com.example.gostsNaumen.entity.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * Позволяет смаппить сущность пользователя из БД в ДТО и наоборот
 */
@Component
public class UserMapper {
    private final PasswordEncoder passwordEncoder;

    public UserMapper(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Используется только во время регистрации нового пользователя.
     *
     * <P>Конвертирует {@link UserDtoRequest} в сущность пригодную для сохранения в БД</P>
     *
     * @param fromWhat ДТО пришедшая извне для регистрации нового пользователя
     * @return сущность готовая для сохранения в БД (примечание: по дефолту роль для всех ставится User)
     */
    public User mapToEntity(UserDtoRequest fromWhat) {
        String passwordHash = passwordEncoder.encode(fromWhat.password());

        return new User(
                fromWhat.userName(),
                fromWhat.fullName(),
                passwordHash,
                fromWhat.email()
        );
    }

    /**
     * Конвертирует сущность из БД в ДТО, готовое для отправки клиенту, без пароля пользователя
     *
     * @param fromWhat сущность пользователя из БД
     * @return дто готовое для отправки клиенту
     */
    public UserDtoResponse mapEntityToDto(User fromWhat) {
        return new UserDtoResponse(
                fromWhat.getId(),
                fromWhat.getUsername(),
                fromWhat.getFullName(),
                fromWhat.getEmail(),
                fromWhat.getRoles().toString()
        );
    }
}