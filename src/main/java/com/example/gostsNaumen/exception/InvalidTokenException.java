package com.example.gostsNaumen.exception;

/**
 * Относится к ряду ошибок невалидности токена
 */
public class InvalidTokenException extends RuntimeException {
    public InvalidTokenException(String message) {
        super(message);
    }
}
