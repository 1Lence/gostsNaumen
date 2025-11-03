package com.example.gostsNaumen.entity.model;

/**
 * Перечисление, содержащее все возможные варианты уровня гармонизации
 */
public enum HarmonizationEnum implements HasValue{
    NON_HARMONIZED("Не гармонизированный"),
    MODIFIED("Модифицированный"),
    HARMONIZED("Гармонизированный");

    private final String value;

    HarmonizationEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
