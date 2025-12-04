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
     * Код, отвечающий за ситуацию, когда статус документа уже установлен
     */
    STATUS_ALREADY_SET("STATUS_ALREADY_SET", "Текущий статус уже установлен", HttpStatus.CONFLICT),
    /**
     * Код, отвечающий за ситуацию, когда происходит попытка совершить невозможный переход по жизненному циклу
     */
    INVALID_LIFECYCLE_TRANSITION(
            "CANT_CHANGE_STATUS",
            "Невозможно совершить текущий переход по жизненному циклу",
            HttpStatus.CONFLICT
    ),
    /**
     * Код, отвечающий за ситуацию, когда пользователя не удается найти по каким-либо данным
     */
    USER_NOT_FOUND("USER_NOT_FOUND", "Пользователь не найден", HttpStatus.NOT_FOUND),
    /**
     * Код, отвечающий за ситуацию, какие-либо поля пользователя уже существуют в БД
     */
    USER_FIELDS_ALREADY_EXIST("USER_FIELDS_ALREADY_EXIST", "Пользователь существует", HttpStatus.CONFLICT),
    /**
     * Код, отвечающий за ситуацию, когда происходит попытка некорректного перехода по жизненному циклу
     */
    INCORRECT_LIFECYCLE_TRANSITION(
            "INCORRECT_LIFECYCLE_TRANSITION",
            "Такой переход по жизненному циклу невозможен",
            HttpStatus.CONFLICT),
    /**
     * Код, отвечающий за ситуацию, когда переходу по жизненному циклу мешает другой стандарт в базе данных.
     */
    OTHER_DOC_INTERFERES_WITH_TRANSITION(
            "OTHER_DOC_INTERFERES_WITH_TRANSITION",
            "Переходу по жизненному циклу мешает другой стандарт",
            HttpStatus.CONFLICT),
    /**
     * Код, отвечающий за ситуацию, когда происходит попытка сохранить документ, актуальная версия которого уже существует
     */
    STANDARD_BY_NAME_WITH_CURRENT_STATUS_ALREADY_EXIST(
            "STANDARD_BY_NAME_WITH_CURRENT_STATUS_ALREADY_EXIST",
            "Уже существует актуальынй стандарт с таким именем, сначала архивируйте его",
            HttpStatus.CONFLICT),
    /**
     * Код, отвечающий за ситуацию, когда происходит попытка загрузки файла, но файл не был передан
     */
    NULL_FILE_DATA_REQUEST("NULL_FILE_DATA", "Не было загружено документа", HttpStatus.BAD_REQUEST),
    /**
     * Код, отвечающий за ситуацию, когда происходит попытка скачивания файла, но файл у ГОСТа отсутствует
     */
    NULL_FILE_DATA_RESPONSE("NULL_FILE_DATA", "Документ у данного госта отсутствует", HttpStatus.BAD_REQUEST),
    /**
     * Код, отвечающий за ситуацию, когда происходит попытка удаления файла, но файл у ГОСТа отсутствует
     */
    FILE_ALREADY_NULL("FILE_ALREADY_NULL", "Файл отсутствует у ГОСТа", HttpStatus.BAD_REQUEST),
    /**
     * Код, отвечающий за ситуацию, когда происходит попытка <b>сохранения</b>, но гост уже имеет файл
     */
    FILE_ALREADY_EXIST("FILE_ALREADY_NULL", "Файл уже существует у ГОСТа", HttpStatus.CONFLICT),
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