package com.example.gostsNaumen.controller.dto;

import com.example.gostsNaumen.controller.TwoWaysMapper;
import com.example.gostsNaumen.controller.dto.request.UserDtoRequest;
import com.example.gostsNaumen.controller.dto.response.UserDtoResponse;
import com.example.gostsNaumen.entity.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * Позволяет смаппить сущность пользователя из БД в ДТО
 */
@Component
public class UserMapper implements TwoWaysMapper<User, UserDtoRequest, UserDtoResponse> {
    private final PasswordEncoder passwordEncoder;

    public UserMapper(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User mapToEntity(UserDtoRequest fromWhat) {
        String passwordHash = passwordEncoder.encode(fromWhat.password());
        return new User(fromWhat.userName(), passwordHash, fromWhat.email());
    }

    @Override
    public UserDtoResponse mapEntityToDto(User fromWhat) {
        return new UserDtoResponse(
                fromWhat.getId(),
                fromWhat.getUsername(),
                fromWhat.getEmail(),
                fromWhat.getRoles().toString()
        );
    }
}