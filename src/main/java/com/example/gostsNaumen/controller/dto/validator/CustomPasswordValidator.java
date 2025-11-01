package com.example.gostsNaumen.controller.dto.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.stereotype.Component;

/**
 * Валидатор для проверки корректности пароля по паттерну.
 * Описание паттерна:
 * <ul>
 *     <li>Минимальная длина 8, максимальная 256</li>
 *     <li>Обязательно один спец. символ</li>
 *     <li>Одна заглавная буква</li>
 * </ul>
 */
@Component
public class CustomPasswordValidator implements ConstraintValidator<CustomPasswordValid, String> {
    private final String PASSWORD_REGEX =
            "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*()_+\\-=\\[\\]{}|;':\",./<>?]).{8,256}$";

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        return s != null && !s.isEmpty() && s.matches(PASSWORD_REGEX);
    }
}