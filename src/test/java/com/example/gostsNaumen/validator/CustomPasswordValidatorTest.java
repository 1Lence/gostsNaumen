package com.example.gostsNaumen.validator;

import com.example.gostsNaumen.controller.dto.validator.CustomPasswordValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * Проверка валидатора пароля
 */
@ExtendWith(MockitoExtension.class)
class CustomPasswordValidatorTest {

    @InjectMocks
    private CustomPasswordValidator validator;

    @Mock
    private ConstraintValidatorContext context;

    @BeforeEach
    void setUp() {
        validator = new CustomPasswordValidator();
    }

    /**
     * Проверяются различные корректные варианты паролей
     */
    @Test
    void shouldAcceptValidPasswords() {
        String[] validPasswords = {
                "Password1!",
                "MySecure123@",
                "TestPass1#",
                "aB1!abcdE",
                "ValidPass123$",
                "StrongPassword1@",
                "P@ssw0rd1234567890"
        };

        for (String password : validPasswords) {
            Assertions.assertTrue(validator.isValid(password, context));
        }
    }

    /**
     * Проверяется, что валидатор не позволит ввести пустой пароль
     */
    @Test
    void shouldRejectNullAndEmptyPasswords() {
        String nullPassword = null;
        String emptyPassword = "";
        String blankPassword = "   ";

        Assertions.assertFalse(validator.isValid(nullPassword, context), "Должен отклонить null значение");
        Assertions.assertFalse(validator.isValid(emptyPassword, context), "Должен отклонить пустую строку");
        Assertions.assertFalse(validator.isValid(blankPassword, context), "Должен отклонить строку с пробелами");
    }

    /**
     * Проверяется, что валидатор должен отклонить пароль без заглавной буквы
     */
    @Test
    void shouldRejectPasswordWithoutUppercaseLetter() {
        String[] invalidPasswords = {
                "password1!",
                "test123@",
                "12345678a!"
        };

        for (String password : invalidPasswords) {
            Assertions.assertFalse(validator.isValid(password, context));
        }
    }

    /**
     * Проверяется, что валидатор должен отклонить пароль только с заглавными буквами
     */
    @Test
    void shouldRejectPasswordWithoutLowercaseLetter() {
        String[] invalidPasswords = {
                "PASSWORD1!",
                "TEST123@",
                "12345678A!"
        };

        for (String password : invalidPasswords) {
            Assertions.assertFalse(validator.isValid(password, context));
        }
    }

    /**
     * Проверяется, что валидатор должен отклонить пароль без цифры
     */
    @Test
    void shouldRejectPasswordWithoutDigit() {
        String[] invalidPasswords = {
                "Password!",
                "TestPass@",
                "MySecurePass#"
        };

        for (String password : invalidPasswords) {
            Assertions.assertFalse(validator.isValid(password, context));
        }
    }

    /**
     * Проверяется, что валидатор должен отклонить пароль без специального символа
     */
    @Test
    void shouldRejectPasswordWithoutSpecialCharacter() {
        String[] invalidPasswords = {
                "Password1",
                "TestPass123",
                "MySecure123456"
        };

        for (String password : invalidPasswords) {
            Assertions.assertFalse(validator.isValid(password, context));
        }
    }

    /**
     * Проверяется, что валидатор должен отклонить пароль менее 8 символов
     */
    @Test
    void shouldRejectPasswordTooShort() {
        String[] invalidPasswords = {
                "Aa1!",
                "Pass1@",
                "Test1!A",
                "Sh0rt!"
        };

        for (String password : invalidPasswords) {
            Assertions.assertFalse(validator.isValid(password, context));
        }
    }

    /**
     * Проверяется, что валидатор должен принять пароль ровно на 8 символов
     */
    @Test
    void shouldAcceptPasswordAtMinimumLength() {
        String password = "Pass1!A@";

        Assertions.assertTrue(validator.isValid(password, context));
    }

    /**
     * Проверяется, что валидатор должен принять пароль с различными спец символами
     */
    @Test
    void shouldAcceptVariousSpecialCharacters() {
        String[] validPasswords = {
                "Password1!",
                "Password1@",
                "Password1#",
                "Password1$",
                "Password1%",
                "Password1^",
                "Password1&",
                "Password1*",
                "Password1(",
                "Password1)",
                "Password1_",
                "Password1+",
                "Password1=",
                "Password1-",
                "Password1[",
                "Password1]",
                "Password1{",
                "Password1}",
                "Password1|",
                "Password1;",
                "Password1:",
                "Password1'",
                "Password1\"",
                "Password1,",
                "Password1.",
                "Password1/",
                "Password1<",
                "Password1>",
                "Password1?",
        };

        for (String password : validPasswords) {
            Assertions.assertTrue(validator.isValid(password, context));
        }
    }

    /**
     * Проверяется, что валидатор должен принять пароль длиной 255 символов
     */
    @Test
    void shouldHandleEdgeCases() {
        String maxLengthPassword = "Aa1!" + "x".repeat(252);

        Assertions.assertTrue(validator.isValid(maxLengthPassword, context));
    }

    /**
     * Проверяется, что валидатор должен принять пароль длиной 255 символов
     */
    @Test
    void shouldRejectPasswordTooLong() {
        String tooLongPassword = "Aa1!" + "x".repeat(253);

        Assertions.assertFalse(validator.isValid(tooLongPassword, context));
    }
}