package com.example.gostsNaumen.entity.model;

/**
 * Статус документа.
 * <p>Используется для отслеживания текущего состояния документа в жизненном цикле.</p>
 */
public enum StatusEnum implements HasValue {
    /**
     * Актуальный документ.
     * <p>Документ действующий и используется в работе.</p>
     */
    CURRENT("Актуальный"),
    /**
     * Отменённый документ.
     * <p>Документ больше не действует, но сохраняется в истории.</p>
     */
    CANCELED("Отменённый"),
    /**
     * Заменённый документ.
     * <p>Документ был заменён новым документом, более актуальным.</p>
     */
    REPLACED("Заменённый");

    private final String value;

    StatusEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
