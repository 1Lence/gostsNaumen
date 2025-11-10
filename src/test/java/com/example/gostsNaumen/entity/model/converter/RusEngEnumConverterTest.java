package com.example.gostsNaumen.entity.model.converter;

import com.example.gostsNaumen.entity.model.AdoptionLevelEnum;
import com.example.gostsNaumen.entity.model.HarmonizationEnum;
import com.example.gostsNaumen.entity.model.StatusEnum;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Класс для тестирования конвертера Enum-ов, тестирует как успешные случаи, так и ошибочные
 */
class RusEngEnumConverterTest {

    RusEngEnumConverter rusEngEnumConverter = new RusEngEnumConverter();

    /**
     * Проверяет корректность работы на стандартных данных
     */
    @Test
    void convertToDatabaseColumShouldReturnCorrectValues() {
        assertEquals(
                AdoptionLevelEnum.NATIONAL,
                rusEngEnumConverter.convertToEnglishValue("Национальный", AdoptionLevelEnum.class));
        assertEquals(
                StatusEnum.CURRENT,
                rusEngEnumConverter.convertToEnglishValue("Актуальный", StatusEnum.class)
        );
        assertEquals(
                HarmonizationEnum.HARMONIZED,
                rusEngEnumConverter.convertToEnglishValue("Гармонизированный", HarmonizationEnum.class)
        );
    }

    /**
     * Проверка случая, когда метод получает несуществующее в enum-е значение
     */
    @Test
    void convertToEnglishValueShouldThrowIllegalArgumentExceptionWhenStringIsIncorrect() {
        String wrongTestValue = "неправильноеЗначение";
        Throwable exception = Assertions.assertThrows(IllegalArgumentException.class,
                () -> {
                    rusEngEnumConverter.convertToEnglishValue(wrongTestValue, AdoptionLevelEnum.class);
                });
        Assertions.assertEquals(
                "Нет атрибута: " + wrongTestValue + " для энама " + AdoptionLevelEnum.class,
                exception.getMessage());
    }

    /**
     * Проверка случая, когда один из аргументов функций {@code null}
     */
    @Test
    void convertToEnglishValueShouldThrowIllegalArgumentExceptionWhenOneOfArgsIsNull() {
        String correctValue = "Региональный";

        Throwable exception = Assertions.assertThrows(IllegalArgumentException.class,
                () -> {
                    rusEngEnumConverter.convertToEnglishValue(correctValue, null);
                });
        Assertions.assertEquals("Один из полученных аргументов null", exception.getMessage());

        Throwable secondException = Assertions.assertThrows(IllegalArgumentException.class,
                () -> {
                    rusEngEnumConverter.convertToEnglishValue(null, AdoptionLevelEnum.class);
                });
        Assertions.assertEquals("Один из полученных аргументов null", secondException.getMessage());
    }

    /**
     * Проверка корректности работы, при нормальных данных
     */
    @Test
    void convertToRussianValueShouldReturnCorrectValues() {
        Assertions.assertEquals("Национальный", rusEngEnumConverter.convertToRussianValue(AdoptionLevelEnum.NATIONAL));
        Assertions.assertEquals("Актуальный", rusEngEnumConverter.convertToRussianValue(StatusEnum.CURRENT));
        Assertions.assertEquals("Гармонизированный", rusEngEnumConverter.convertToRussianValue(HarmonizationEnum.HARMONIZED));
    }

    /**
     * Проверка на выброс null при получении null
     */
    @Test
    void convertToRussianValueShouldReturnNull() {
        Assertions.assertNull(rusEngEnumConverter.convertToRussianValue(null));
    }
}