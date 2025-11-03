package com.example.gostsNaumen.entity.model.converter;

import com.example.gostsNaumen.entity.model.HarmonizationEnum;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.springframework.stereotype.Component;

/**
 * Конвертер, позволяющий работать с enum-ом в базе данных.<br>
 * Конвертирует русский текст из запроса в конкретное значение {@link HarmonizationEnum} и наоборот
 */
@Component
@Converter(autoApply = true)
public class HarmonizationLevelConverter implements AttributeConverter<HarmonizationEnum, String> {
    @Override
    public HarmonizationEnum convertToEntityAttribute(String dbData) {
        if (dbData == null) {
            return null;
        }
        for (HarmonizationEnum harmonizationType : HarmonizationEnum.values()) {
            if (harmonizationType.getValue().equals(dbData)) {
                return harmonizationType;
            }
        }
        throw new IllegalArgumentException("No enum constant " + dbData);
    }

    @Override
    public String convertToDatabaseColumn(HarmonizationEnum englishName) {
        return englishName == null ? null : englishName.getValue();
    }
}
