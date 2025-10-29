package com.example.gostsNaumen.security.dto;

/**
 * Дто с токеном обновления.
 * @param refreshToken токен обновления
 */
public record RefreshTokenDto(
        String refreshToken
){
}