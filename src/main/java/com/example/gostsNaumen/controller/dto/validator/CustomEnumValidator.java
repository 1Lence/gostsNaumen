package com.example.gostsNaumen.controller.dto.validator;

import com.example.gostsNaumen.entity.model.HasValue;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Валидатор для enum-ов, имплементирующих {@link HasValue}
 */
@Component
public class CustomEnumValidator implements ConstraintValidator<CustomEnumValid, String> {

    private List<String> acceptedValues;

    /**
     * Перед валидацией необходимо собрать возможные варианты значения из переданного класса.
     *
     * @param annotation
     */
    @Override
    public void initialize(CustomEnumValid annotation) {
        Class<? extends HasValue> enumClass = annotation.enumClass();
        acceptedValues = Stream.of(enumClass.getEnumConstants())
                .map(HasValue::getValue)
                .collect(Collectors.toList());
    }

    /**
     * Метод сверки полученного значения с возможным значением enum-а.
     *
     * @param value   полученная строка.
     * @param context не используется в данной реализации.
     * @return true - есть совпадения, false - нет, проверка провалена
     */
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) return true;
        return acceptedValues.contains(value);
    }
}
