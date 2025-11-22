package com.example.gostsNaumen.exception;

import java.util.Arrays;

/**
 * Ошибка, используемая для реализации бизнес логики.
 * Используется для единообразного и удобного создания Handlers.
 */
public class BusinessException extends RuntimeException {
    private final ErrorCode errorCode;
    private final Object[] args;

    public BusinessException(final ErrorCode errorCode, final Object... args){
        this.errorCode = errorCode;
        this.args = args;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }

    /**
     * Позволяет вывести либо текст ошибки по дефолту из {@link ErrorCode}, либо ту, которую указали при создании ошибки.
     *
     * @return текст ошибки
     */
    public String getFormattedMessage() {
        if (args == null || args.length == 0) {
            return errorCode.getDefaultMessage();
        }

        return Arrays.toString(args).replace("[", "").replace("]", "");
    }
}