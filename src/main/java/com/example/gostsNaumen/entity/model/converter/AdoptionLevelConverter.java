package com.example.gostsNaumen.entity.model.converter;

import com.example.gostsNaumen.entity.model.AdoptionLevelEnum;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.springframework.stereotype.Component;

/**
 * Конвертер, позволяющий работать с enum-ом в базе данных.<br>
 * Конвертирует русский текст из запроса в конкретное значение {@link AdoptionLevelEnum} и наоборот
 */
@Converter(autoApply = true)
@Component
public class AdoptionLevelConverter implements AttributeConverter<AdoptionLevelEnum, String> {
    @Override
    public AdoptionLevelEnum convertToEntityAttribute(String dbData) {
        if (dbData == null) {
            return null;
        }
        for (AdoptionLevelEnum adoptionLevel : AdoptionLevelEnum.values()) {
            if (adoptionLevel.getValue().equals(dbData)) {
                return adoptionLevel;
            }
        }
        throw new IllegalArgumentException("No enum constant " + AdoptionLevelEnum.class.getCanonicalName());
    }

    @Override
    public String convertToDatabaseColumn(AdoptionLevelEnum s) {
        return s == null ? null : s.getValue();
    }
}
