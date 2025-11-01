package com.example.gostsNaumen.exception;

import org.springframework.http.HttpStatus;

/**
 * Набор констант с возможными ошибками в бизнес-логике приложения
 */
public enum ErrorCode {

    NO_SUCH_CODE_OKS("NO_SUCH_CODE_OKS", "Несуществующий код ОКС", HttpStatus.BAD_REQUEST),
    INVALID_TOKEN("INVALID_TOKEN", "Невалидный токен", HttpStatus.UNAUTHORIZED),
    ;

    private final String code;
    private final String defaultMessage;
    private  final HttpStatus status;

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