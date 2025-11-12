package com.example.gostsNaumen.entity.model.converter;

import com.example.gostsNaumen.entity.model.HasValue;
import org.springframework.stereotype.Component;

/**
 * Конвертер. Необходим для преобразования enum-ов между русским и английским значениями.
 */
@Component
public class RusEngEnumConverter {

    /**
     * Метод для получения английского ключа enum-а, по строке с русским соответствующим значением
     *
     * @param s         строка для поиска соответствующего значения
     * @param enumClass класс enum-а, в котором будет производиться поиск соответствующего значения
     * @return возвращает экземпляр enum-а
     */
    public <E extends Enum<E> & HasValue> E convertToEnglishValue(String s, Class<E> enumClass) {
        if (s == null && enumClass == null) {
            throw new IllegalArgumentException("Один из полученных аргументов null");
        }
        for (E enumConstant : enumClass.getEnumConstants()) {
            if (enumConstant.getValue().equals(s)) {
                return enumConstant;
            }
        }
        throw new IllegalArgumentException("Нет значения: " + s + " для энама " + enumClass);
    }

    /**
     * Метод для получения русского значения enum-а по его английскому ключу
     *
     * @param englishValue английский ключ
     * @return возвращает русское значение enum-а
     */
    public <E extends Enum<E> & HasValue> String convertToRussianValue(E englishValue) {
        return englishValue == null ? null : englishValue.getValue();
    }
}
