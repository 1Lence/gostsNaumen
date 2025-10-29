package com.example.gostsNaumen.dto.response;

/**
 * Дто для передачи
 * @param id
 * @param userName
 * @param email
 * @param role
 */
public record UserDtoResponse(
        Long id,
        String userName,
        String email,
        String role
) {
}