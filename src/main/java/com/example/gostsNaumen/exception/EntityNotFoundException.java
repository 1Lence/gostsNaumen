package com.example.gostsNaumen.exception;

/**
 * Ошибка, относящаяся к ситуации, когда по переданным параметрам не обнаружена сущность в базе
 */
public class EntityNotFoundException extends RuntimeException {
    public EntityNotFoundException(String message) {
        super(message);
    }
}
