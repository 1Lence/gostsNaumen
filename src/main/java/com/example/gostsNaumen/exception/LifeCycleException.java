package com.example.gostsNaumen.exception;

/**
 * Ошибка, отвечающая за случаи ошибок в системе переходов по жизненному циклу
 */
public class LifeCycleException extends RuntimeException {
    public LifeCycleException(String message) {
        super(message);
    }
}
