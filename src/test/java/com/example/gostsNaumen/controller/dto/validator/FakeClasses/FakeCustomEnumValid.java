package com.example.gostsNaumen.controller.dto.validator.FakeClasses;

import com.example.gostsNaumen.controller.dto.validator.CustomEnumValid;
import com.example.gostsNaumen.entity.model.HasValue;
import jakarta.validation.Payload;

import java.lang.annotation.Annotation;

/**
 * Манекен аннотации CustomEnumValid
 */
public class FakeCustomEnumValid implements CustomEnumValid {

    /**
     * Класс enum-а по значениям которого проводится валидация
     */
    private final Class<? extends HasValue> enumClass;

    public FakeCustomEnumValid(Class<? extends HasValue> enumClass) {
        this.enumClass = enumClass;
    }

    @Override
    public Class<? extends HasValue> enumClass() {
        return enumClass;
    }

    @Override
    public String message() {
        return "";
    }

    @Override
    public Class<?>[] groups() {
        return new Class[0];
    }

    @Override
    public Class<? extends Payload>[] payload() {
        return new Class[0];
    }

    @Override
    public Class<? extends Annotation> annotationType() {
        return null;
    }
}
