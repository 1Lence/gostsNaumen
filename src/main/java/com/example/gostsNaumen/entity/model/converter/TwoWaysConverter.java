package com.example.gostsNaumen.entity.model.converter;

import com.example.gostsNaumen.entity.model.HasValue;
import org.springframework.stereotype.Component;

@Component
public class TwoWaysConverter {

    public <E extends Enum<E> & HasValue> E convertToDatabaseColumn(String s, Class<E> enumClass) {
        if (s == null) {
            return null;
        }
        for (E enumConstant : enumClass.getEnumConstants()) {
            if (enumConstant.getValue().equals(s)) {
                return enumConstant;
            }
        }
        throw new IllegalArgumentException("No enum constant " + s + " for " + enumClass);
    }

    public <E extends Enum<E> & HasValue> String convertToEntityAttribute(E englishValue) {
        return englishValue == null ? null : englishValue.getValue();
    }
}
