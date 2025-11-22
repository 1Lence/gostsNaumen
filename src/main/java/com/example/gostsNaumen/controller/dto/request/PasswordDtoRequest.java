package com.example.gostsNaumen.controller.dto.request;

import com.example.gostsNaumen.controller.dto.validator.CustomPasswordValid;

/**
 * Дто, в котором передается новый пароль пользователя для его дальнейшего изменения
 *
 * @param newPassword новый пароль пользователя
 */
public record PasswordDtoRequest(
        @CustomPasswordValid String newPassword
) {
}
