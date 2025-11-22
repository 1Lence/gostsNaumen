package com.example.gostsNaumen.security.dto;

import jakarta.validation.constraints.NotBlank;

/**
 * Дто с входными данными пользователя.
 * @param email
 * @param password
 */
public record UserCredentialsDto(
        @NotBlank String email,
        @NotBlank String password
) {
}