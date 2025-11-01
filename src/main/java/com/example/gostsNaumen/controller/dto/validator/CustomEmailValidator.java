package com.example.gostsNaumen.controller.dto.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.stereotype.Component;

/**
 * Валидатор для проверки корректности почты по паттерну.
 *
 * <ul>
 *     <li>До @ должны быть какие-либо символы</li>
 *     <li>@ - обязательный символ</li>
 *     <li>Доменная часть должна содержать от 2 до 7 символов</li>
 * </ul>
 */
@Component
public class CustomEmailValidator implements ConstraintValidator<CustomEmailValid, String> {
    private final String EMAIL_REGEX =
            "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";

    /**
     * Метод сверки введённой почты с заданным паттерном.
     *
     * @param email                      введённая почта.
     * @param constraintValidatorContext не используется в данной реализации.
     * @return true - почта введена верно, false - неверно
     */
    @Override
    public boolean isValid(String email, ConstraintValidatorContext constraintValidatorContext) {
        return email != null && !email.isEmpty() && email.matches(EMAIL_REGEX);
    }
}