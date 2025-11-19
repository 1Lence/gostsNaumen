package com.example.gostsNaumen.controller.dto.response;

/**
 * DTO с данными пользователя для передачи его клиенту, не имеет пароля пользователя
 * @param id
 * @param userName
 * @param fullName
 * @param email
 * @param role
 */
public record UserDtoResponse(
        Long id,
        String userName,
        String fullName,
        String email,
        String role
) {
}