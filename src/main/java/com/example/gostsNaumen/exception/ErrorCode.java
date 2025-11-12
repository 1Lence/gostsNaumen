package com.example.gostsNaumen.exception;

import org.springframework.http.HttpStatus;

/**
 * Набор констант с возможными ошибками в бизнес-логике приложения
 */
public enum ErrorCode {
    /**
     * Код, отвечающий за ситуацию, когда по коду окс ничего не найдено
     */
    NO_SUCH_CODE_OKS("NO_SUCH_CODE_OKS", "Несуществующий код ОКС", HttpStatus.BAD_REQUEST),
    /**
     * Код, отвечающий за ситуацию, когда получен невалидный токен
     */
    INVALID_TOKEN("INVALID_TOKEN", "Невалидный токен", HttpStatus.UNAUTHORIZED),
    /**
     * Код, отвечающий за ситуацию, когда статус документа уже установлен
     */
    STATUS_ALREADY_SET("STATUS_ALREADY_SET", "Текущий статус уже установлен", HttpStatus.I_AM_A_TEAPOT),
    /**
     * Код, отвечающий за ситуацию, когда происходит попытка совершить невозможный переход по жизненному циклу
     */
    INVALID_LIFECYCLE_TRANSITION(
            "CANT_CHANGE_STATUS",
            "Невозможно совершить текущий переход по жизненному циклу",
            HttpStatus.I_AM_A_TEAPOT
    );
    private final String code;
    private final String defaultMessage;
    private final HttpStatus status;

    ErrorCode(final String code,
              final String defaultMessage,
              final HttpStatus status) {
        this.code = code;
        this.defaultMessage = defaultMessage;
        this.status = status;
    }

    public String getCode() {
        return code;
    }

    public String getDefaultMessage() {
        return defaultMessage;
    }

    public HttpStatus getStatus() {
        return status;
    }
}