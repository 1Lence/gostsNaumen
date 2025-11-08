package com.example.gostsNaumen.controller.dto.request;

import com.example.gostsNaumen.controller.dto.validator.CustomEnumValid;
import com.example.gostsNaumen.entity.model.StatusEnum;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

/**
 * Дто для приёма id госта и его нового статуса
 */
public class DocumentIdStatusDtoRequest {
    @NotNull
    private Long id;
    @CustomEnumValid(enumClass = StatusEnum.class, message = "Должно содержать: Актуальный, Отменённый или Заменённый")
    @NotEmpty
    private String status;

    public DocumentIdStatusDtoRequest() {
    }

    public Long getId() {
        return id;
    }

    public DocumentIdStatusDtoRequest setId(Long id) {
        this.id = id;
        return this;
    }

    public String getStatus() {
        return status;
    }

    public DocumentIdStatusDtoRequest setStatus(String status) {
        this.status = status;
        return this;
    }
}
