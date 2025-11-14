package com.example.gostsNaumen.controller.dto.request;

import com.example.gostsNaumen.controller.dto.validator.CustomPasswordValid;

public record PasswordDtoRequest(
        @CustomPasswordValid String newPassword
) {
}
