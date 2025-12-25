package com.example.gostsNaumen.entity.model;

/**
 * Указывает на то, был документ введён впервые или уже изменялся.
 */
public enum AcceptedFirstTimeOrReplacedEnum implements HasValue {
    /**
     * Документ был введён впервые и не изменялся
     */
    FIRST_TIME("ВВЕДЕН ВПЕРВЫЕ"),
    /**
     * Документ однажды был отредактирован
     */
    REPLACED("ИЗМЕНЁН");

    private final String value;

    AcceptedFirstTimeOrReplacedEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
