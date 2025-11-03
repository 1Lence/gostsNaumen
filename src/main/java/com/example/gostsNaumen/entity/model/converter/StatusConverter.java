package com.example.gostsNaumen.entity.model.converter;

import com.example.gostsNaumen.entity.model.StatusEnum;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.springframework.stereotype.Component;

/**
 * Конвертер, позволяющий работать с enum-ом в базе данных.<br>
 * Конвертирует русский текст из запроса в конкретное значение {@link StatusEnum} и наоборот
 */
@Component
@Converter(autoApply = true)
public class StatusConverter implements AttributeConverter<StatusEnum, String> {
    @Override
    public StatusEnum convertToEntityAttribute(String dbData) {
        if (dbData == null) {
            return null;
        }
        for (StatusEnum statusType : StatusEnum.values()) {
            if (statusType.getValue().equals(dbData)) {
                return statusType;
            }
        }
        throw new IllegalArgumentException("No enum constant: " + dbData);
    }

    @Override
    public String convertToDatabaseColumn(StatusEnum englishName) {
        return englishName == null ? null : englishName.getValue();
    }
}
