package com.example.gostsNaumen.controller.dto.request;

import com.example.gostsNaumen.controller.dto.validator.CustomEnumValid;
import com.example.gostsNaumen.entity.model.StatusEnum;

/**
 * Дто, в котором передается новый статус пользователя для его дальнейшего изменения
 *
 * @param newStatus новый статус документа
 */
public record NewStatusDtoRequest(@CustomEnumValid(enumClass = StatusEnum.class,
        message = "Должно содержать: Актуальный, Отменённый или Заменённый") String newStatus) {
}
