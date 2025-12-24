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
        /**
         * Уникальный идентификатор пользователя
         */
        Long id,
        /**
         * Никнейм пользователя
         */
        String userName,
        /**
         * Полное имя пользователя
         */
        String fullName,
        /**
         * Почта пользователя
         */
        String email,
        /*
        Роль пользователя
         */
        String role
) {
}