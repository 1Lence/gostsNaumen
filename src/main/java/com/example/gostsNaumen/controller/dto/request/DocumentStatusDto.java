package com.example.gostsNaumen.controller.dto.request;

import com.example.gostsNaumen.controller.dto.validator.CustomEnumValid;
import com.example.gostsNaumen.entity.model.StatusEnum;
import jakarta.validation.constraints.NotEmpty;

/**
 * Дто для приёма id госта и его нового статуса
 */
public class DocumentStatusDto {
    @CustomEnumValid(enumClass = StatusEnum.class, message = "Должно содержать: Актуальный, Отменённый или Заменённый")
    @NotEmpty
    private String status;

    public DocumentStatusDto() {
    }

    public String getStatus() {
        return status;
    }

    public DocumentStatusDto setStatus(String status) {
        this.status = status;
        return this;
    }
}
