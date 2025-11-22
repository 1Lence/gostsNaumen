package com.example.gostsNaumen.controller.dto.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Аннотация используемая для проверки корректности введённой почты.
 */
@Constraint(validatedBy = CustomNullAndRegexEmailValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface CustomNullAndRegexEmailValid {
    String message() default "Поле должно null или почта должна содержать домен '@example.ex' ";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
