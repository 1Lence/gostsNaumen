package com.example.gostsNaumen.exception;

/**
 * Ошибка, используемая для реализации бизнес логики.
 * Используется для единообразного и удобного создания Handlers.
 */
public class BusinessException extends RuntimeException {
    private final ErrorCode errorCode;
    private final Object[] args;

    public BusinessException(final ErrorCode errorCode, final Object... args){
        super(getFormatterMessage(errorCode, args));
        this.errorCode = errorCode;
        this.args = args;
    }

    /**
     * Отформатировать сообщение об ошибке.
     * @param errorCode код ошибки.
     * @param args аргументы
     * @return отформатированное сообщение
     */
    private static String getFormatterMessage(ErrorCode errorCode, Object[] args) {
        if (args == null || args.length == 0){
            return String.format(errorCode.getDefaultMessage(), args);
        }
        return errorCode.getDefaultMessage();
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
