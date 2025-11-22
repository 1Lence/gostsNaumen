package com.example.gostsNaumen.controller.dto.validator;

import jakarta.validation.ConstraintValidatorContext;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * Проверяет корректность валидации email-адресов с использованием кастомного валидатора,
 * который допускает null-значения и применяет регулярное выражение для проверки формата.
 */
@ExtendWith(MockitoExtension.class)
class CustomNullAndRegexEmailValidatorTest {

    /**
     * Экземпляр тестируемого валидатора.
     * Используется для проверки соответствия почты заданным критериям.
     */
    private final CustomNullAndRegexEmailValidator validator;

    /**
     * Контекст валидации JSR-303 (Bean Validation).
     * Используется для передачи информации о контексте валидации валидатору.
     * Может быть использован для настройки сообщений об ошибках или других параметров.
     */
    private final ConstraintValidatorContext context;

    public CustomNullAndRegexEmailValidatorTest(
            @Mock ConstraintValidatorContext context
    ) {
        this.validator = new CustomNullAndRegexEmailValidator();
        this.context = context;
    }

    /**
     * Проверяются различные корректные варианты почты
     */
    @Test
    void shouldAcceptValidEmails() {
        String[] validEmails = {
                "test@example.com",
                "user.name@domain.co.uk",
                "user+tag@example.org",
                "user123@test-domain.com",
                "a@b.co",
                "very.long.email.address@very.long.domain.name.com"
        };

        for (String email : validEmails) {
            Assertions.assertTrue(validator.isValid(email, context));
        }
    }

    /**
     * Проверяются различные некорректные варианты почты
     */
    @Test
    void shouldRejectInvalidEmails() {
        String[] invalidEmails = {
                "",
                " ",
                "invalid-email",
                "@example.com",
                "test@",
                "test@.",
                "test@example.",
                "test@.com",
                "test@@example.com",
                "test@example..com",
                "test example.com",
                "test@exam ple.com",
                "test@exam!ple.com",
                "test@exam#ple.com",
                ".test@example.com",
                "test.@example.com"
        };

        for (String email : invalidEmails) {
            Assertions.assertFalse(validator.isValid(email, context));
        }
    }

    /**
     * Проверяются поддержка специальных символов
     */
    @Test
    void shouldHandleSpecialCharacters() {
        String[] specialCharEmails = {
                "user+tag@example.com",
                "user_name@example.com",
                "user-name@example.com",
                "user*tag@example.com",
                "user&tag@example.com"
        };

        for (String email : specialCharEmails) {
            Assertions.assertTrue(validator.isValid(email, context));
        }
    }

    /**
     * Проверяется корректность обработки почты на верный вариант и неверный
     */
    @Test
    void shouldValidateRegexPattern() {
        String email = "test@example.com";
        String invalidEmail = "invalid-email";

        Assertions.assertTrue(validator.isValid(email, context));
        Assertions.assertFalse(validator.isValid(invalidEmail, context));
    }

    /**
     * Проверяется, что валидатор не позволит ввести пустую почту
     */
    @Test
    void shouldHandleEmptyAndBlankStrings() {
        String emptyString = "";
        String blankString = "   ";
        String nullString = null;

        Assertions.assertFalse(validator.isValid(emptyString, context));
        Assertions.assertFalse(validator.isValid(blankString, context));
        Assertions.assertTrue(validator.isValid(nullString, context));
    }
}