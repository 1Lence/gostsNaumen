package com.example.gostsNaumen.entity.model.converter;

import com.example.gostsNaumen.entity.model.AdoptionLevelEnum;
import com.example.gostsNaumen.entity.model.HarmonizationEnum;
import com.example.gostsNaumen.entity.model.StatusEnum;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TwoWaysConverterTest {

    TwoWaysConverter twoWaysConverter = new TwoWaysConverter();

    /**
     * Проверяет корректность работы на стандартных данных
     */
    @Test
    void convertToDatabaseColumShouldReturnCorrectValues() {
        assertEquals(
                AdoptionLevelEnum.NATIONAL,
                twoWaysConverter.convertToDatabaseColumn("Национальный", AdoptionLevelEnum.class));
        assertEquals(
                StatusEnum.CURRENT,
                twoWaysConverter.convertToDatabaseColumn("Актуальный", StatusEnum.class)
        );
        assertEquals(
                HarmonizationEnum.HARMONIZED,
                twoWaysConverter.convertToDatabaseColumn("Гармонизированный", HarmonizationEnum.class)
        );
    }

    /**
     * Проверка случая, когда метод получает несуществующее в enum-е значение
     */
    @Test
    void convertToDatabaseColumnShouldThrowIllegalArgumentExceptionWhenStringIsIncorrect() {
        String wrongTestValue = "неправильноеЗначение";
        Throwable exception = assertThrows(IllegalArgumentException.class,
                () -> {
                    twoWaysConverter.convertToDatabaseColumn(wrongTestValue, AdoptionLevelEnum.class);
                });
        assertEquals(
                "Нет атрибута: " + wrongTestValue + " для энама " + AdoptionLevelEnum.class,
                exception.getMessage());
    }

    /**
     * Проверка случая, когда один из аргументов функций {@code null}
     */
    @Test
    void convertToDatabaseColumnShouldThrowIllegalArgumentExceptionWhenOneOfArgsIsNull() {
        String correctValue = "Региональный";

        Throwable exception = assertThrows(IllegalArgumentException.class,
                () -> {
                    twoWaysConverter.convertToDatabaseColumn(correctValue, null);
                });
        assertEquals("Один из полученных аргументов null", exception.getMessage());

        Throwable secondException = assertThrows(IllegalArgumentException.class,
                () -> {
                    twoWaysConverter.convertToDatabaseColumn(null, AdoptionLevelEnum.class);
                });
        assertEquals("Один из полученных аргументов null", secondException.getMessage());
    }

    /**
     * Проверка корректности работы, при нормальных данных
     */
    @Test
    void convertToEntityAttributeShouldReturnCorrectValues() {
        assertEquals("Национальный", twoWaysConverter.convertToEntityAttribute(AdoptionLevelEnum.NATIONAL));
        assertEquals("Актуальный", twoWaysConverter.convertToEntityAttribute(StatusEnum.CURRENT));
        assertEquals("Гармонизированный", twoWaysConverter.convertToEntityAttribute(HarmonizationEnum.HARMONIZED));
    }

    /**
     * Проверка на выброс null при получении null
     */
    @Test
    void convertToEntityAttributeShouldReturnNull() {
        assertNull(twoWaysConverter.convertToEntityAttribute(null));
    }
}