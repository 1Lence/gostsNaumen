package com.example.gostsNaumen.handler;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;

/**
 * Базовый класс для ControllerAdvice.
 */
@ControllerAdvice
public abstract class BaseControllerAdvice {

    /**
     * Получение URL по которому произошла ошибка.
     * @param request интерфейс для получения данных из HTTP запроса
     * @return url
     */
    protected String getUrl(WebRequest request) {
        return request.getDescription(false).replace("uri=", "");
    }
}