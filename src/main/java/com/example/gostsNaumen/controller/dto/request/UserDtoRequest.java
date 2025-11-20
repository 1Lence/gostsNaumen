package com.example.gostsNaumen.controller.dto.request;

import com.example.gostsNaumen.controller.dto.validator.CustomEmailValid;
import com.example.gostsNaumen.controller.dto.validator.CustomPasswordValid;
import jakarta.validation.constraints.NotBlank;

/**
 * Дто с данными пользователя, которое проходит с фронтенда.
 * @param userName имя/никнейм пользователя.
 * @param fullName полное имя пользователя.
 * @param password пароль пользователя.
 * @param email почта пользователя.
 */
public record UserDtoRequest(
        @NotBlank String userName,
        @NotBlank String fullName,
        @CustomPasswordValid String password,
        @CustomEmailValid String email
) {
}