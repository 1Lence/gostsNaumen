package com.example.gostsNaumen.entity.model;

/**
 * Перечисление, содержащее все возможные варианты уровня принятия
 */
public enum AdoptionLevelEnum {
    NATIONAL("Национальный"),
    INTERSTATE("Межгосударственный"),
    SECTORAL("Отраслевой"),
    REGIONAL("Региональный"),
    ORGANIZATIONAL_STANDARD("Стандарт Организаций");

    private final String value;

    AdoptionLevelEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
