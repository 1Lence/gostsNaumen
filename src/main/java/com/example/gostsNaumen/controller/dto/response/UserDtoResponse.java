package com.example.gostsNaumen.controller.dto.response;

/**
 * DTO с данными пользователя для передачи его клиенту, не имеет пароля пользователя
 *
 * @param id       идентификатор пользователя
 * @param userName имя пользователя
 * @param fullName полное имя пользователя
 * @param email    почта пользователя
 * @param role     роль пользователя
 */
public record UserDtoResponse(
        Long id,
        String userName,
        String fullName,
        String email,
        String role
) {
}