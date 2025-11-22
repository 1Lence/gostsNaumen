package com.example.gostsNaumen.security.dto;

/**
 * Дто с токенами пользователя и айди. Используется для проверки валидности токенов.
 * @param token основной токен пользователя по которому осуществляется взаимодействие.
 * @param refreshToken токен используемый для обновления основного токена пользователя.
 * @param userId айди пользователя из БД.
 */
public record JwtAuthDto (
        String token,
        String refreshToken,
        Long userId
) {
}