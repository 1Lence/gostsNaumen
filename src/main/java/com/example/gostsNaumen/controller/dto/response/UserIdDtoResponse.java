package com.example.gostsNaumen.controller.dto.response;

/**
 * Дто, которое в себе хранит Id пользователя при ответах на запросы
 *
 * @param id айди пользователя из БД
 */
public record UserIdDtoResponse(Long id) {
}