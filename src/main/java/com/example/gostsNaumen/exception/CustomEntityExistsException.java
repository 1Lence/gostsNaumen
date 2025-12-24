package com.example.gostsNaumen.exception;

/**
 * Ошибка, относящаяся к ситуации, когда сущность уже существует
 */
public class CustomEntityExistsException extends RuntimeException {
    public CustomEntityExistsException(String message) {
        super(message);
    }
}
