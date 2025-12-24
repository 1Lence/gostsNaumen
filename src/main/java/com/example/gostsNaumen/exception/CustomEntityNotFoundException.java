package com.example.gostsNaumen.exception;

/**
 * Ошибка, относящаяся к ситуации, когда по переданным параметрам не обнаружена сущность в базе
 */
public class CustomEntityNotFoundException extends RuntimeException {
    public CustomEntityNotFoundException(String message) {
        super(message);
    }
}
