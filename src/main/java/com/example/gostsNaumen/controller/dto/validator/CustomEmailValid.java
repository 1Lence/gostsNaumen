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
@Constraint(validatedBy = CustomEmailValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface CustomEmailValid {
    String message() default "Поле должно быть заполнено и/или почта должна содержать домен '@example.ex' ";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}