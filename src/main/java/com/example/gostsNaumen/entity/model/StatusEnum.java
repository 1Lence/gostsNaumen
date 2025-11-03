package com.example.gostsNaumen.entity.model;

/**
 * Перечисление, содержащее все возможные варианты статуса документа
 */
public enum StatusEnum implements HasValue {
    CURRENT("Актуальный"),
    CANCELED("Отменённый"),
    REPLACED("Заменённый");

    private final String value;

    StatusEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
