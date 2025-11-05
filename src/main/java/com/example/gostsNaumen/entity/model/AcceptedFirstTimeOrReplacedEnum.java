package com.example.gostsNaumen.entity.model;

/**
 * Перечисление, содержащее варианты состояния документа
 */
public enum AcceptedFirstTimeOrReplacedEnum implements HasValue {
    FIRST_TIME("ВВЕДЕН ВПЕРВЫЕ"),
    REPLACED("ИЗМЕНЁН");

    private final String value;

    AcceptedFirstTimeOrReplacedEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
