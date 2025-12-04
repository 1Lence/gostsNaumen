package com.example.gostsNaumen.controller.dto.response;

/**
 * Дто для ответа на успешную загрузку
 *
 * @param gostId айди ГОСТа из БД к которому был загружен файл
 */
public record FileIdDtoResponse(Long gostId) {
}