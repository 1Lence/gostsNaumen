package com.example.gostsNaumen.controller.dto.request;

import com.example.gostsNaumen.controller.dto.validator.CustomEmailValid;
import com.example.gostsNaumen.security.permission.UserRoles;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * Дто с данными пользователя, которые необходимо обновить.
 *
 * @param userName имя/никнейм пользователя.
 * @param fullName полное имя пользователя.
 * @param email    почта пользователя.
 * @param role     роли пользователя
 */
public record UpdateUserDtoRequest(
        @NotBlank String userName,
        @NotBlank String fullName,
        @CustomEmailValid String email,
        @NotNull UserRoles role
) {
}