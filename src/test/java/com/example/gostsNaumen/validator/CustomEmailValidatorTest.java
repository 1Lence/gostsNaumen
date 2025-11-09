package com.example.gostsNaumen.validator;

import com.example.gostsNaumen.controller.dto.validator.CustomEmailValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * Тестирование валидации почты
 */
@ExtendWith(MockitoExtension.class)
class CustomEmailValidatorTest {

    @InjectMocks
    private CustomEmailValidator validator;

    @Mock
    private ConstraintValidatorContext context;

    /**
     * Подготовка валидатора
     */
    @BeforeEach
    void setUp() {
        validator = new CustomEmailValidator();
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
                null,
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
        Assertions.assertFalse(validator.isValid(nullString, context));
    }
}