package com.example.gostsNaumen.controller.dto.request;

import com.example.gostsNaumen.controller.dto.validator.CustomNullAndRegexEmailValid;

/**
 * Дто с данными пользователя, которые необходимо обновить.
 *
 * @param userName имя/никнейм пользователя.
 * @param fullName полное имя пользователя.
 * @param email    почта пользователя.
 * @param role     роли пользователя
 */
public record UpdateUserDtoRequest(
        String userName,
        String fullName,
        @CustomNullAndRegexEmailValid String email,
        String role
) {
}