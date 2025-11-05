package com.example.gostsNaumen.controller.dto.validator;

import com.example.gostsNaumen.entity.model.HasValue;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Аннотация используемая для проверки корректности введённого поля, где {@code String} значение поля это {@code .getValue()}
 * полученного в аннотации enum-а.
 */
@Constraint(validatedBy = CustomEnumValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface CustomEnumValid {
    Class<? extends HasValue> enumClass();

    String message() default "Должно быть одним из значений value";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}