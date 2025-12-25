package com.example.gostsNaumen.controller.dto.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Аннотация используемая для проверки корректности введённого пароля.
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = CustomPasswordValidator.class)
public @interface CustomPasswordValid {
    String message() default "Невалидная почта";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}