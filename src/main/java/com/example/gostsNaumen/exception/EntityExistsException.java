package com.example.gostsNaumen.exception;

/**
 * Ошибка, относящаяся к ситуации, когда сущность уже существует
 */
public class EntityExistsException extends RuntimeException {
    public EntityExistsException(String message) {
        super(message);
    }
}
