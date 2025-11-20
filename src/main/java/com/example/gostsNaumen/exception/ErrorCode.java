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
     * Код, отвечающий за ситуацию, когда при поиске сущности документа в бд, не находятся совпадения
     */
    STANDARD_BY_ID_NOT_EXISTS("CANT_FIND_STANDARD_BY_ID", "По переданному id нет стандарта", HttpStatus.NOT_FOUND),
    /**
     * Код, отвечающий за ситуацию, когда пользователя не удается найти по каким-либо данным
     */
    USER_NOT_FOUND("USER_NOT_FOUND", "Пользователь не найден", HttpStatus.NOT_FOUND),
    /**
     * Код, отвечающий за ситуацию, какие-либо поля пользователя уже существуют в БД
     */
    USER_FIELDS_ALREADY_EXIST("USER_FIELDS_ALREADY_EXIST", "Пользователь существует", HttpStatus.CONFLICT),
    /**
     * Код, отвечающий за ситуацию, когда документ уже существует по полному имени
     */
    STANDARD_EXIST_BY_FULL_NAME("STANDARD_EXIST_BY_FULL_NAME", "ГОСТ с таким именем уже существует", HttpStatus.CONFLICT),
    ;

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