package com.example.gostsNaumen.handler;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import static org.springframework.http.HttpStatus.CONFLICT;

/**
 * Централизованный обработчик ошибок взаимодействия с репозиторием.
 * Здесь необходимо отлавливать ошибки связанные с валидацией/внешней работой приложения.
 */
@ControllerAdvice
public class RepositoryExceptionHandler extends BaseControllerAdvice {
    private Logger log = LoggerFactory.getLogger(RepositoryExceptionHandler.class);

    /**
     * Отлавливает ошибки связанные с отсутствием сущностей в БД
     *
     * @param exception возникает при отсутствии сущности при поиске по какому-либо параметру
     * @param request   http запрос
     * @return HTTP Status код и JSON с ответом
     */
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleEntityNotFound(EntityNotFoundException exception, WebRequest request) {
        log.info("EntityNotFoundException: {}", exception.getMessage());
        log.debug(exception.getMessage(), exception);

        return new ResponseEntity<>(
                new ErrorResponse()
                        .setStatus(CONFLICT)
                        .setUrl(getUrl(request)),
                CONFLICT
        );
    }

    /**
     * Отлавливает ошибки связанные с попыткой добавить сущность/данные в сущность,
     * которые уже существуют в бд и/или помечены как unique.
     *
     * @param exception возникает при отсутствии сущности при попытке вставить повторяющиеся данные
     * @param request   http запрос
     * @return HTTP Status код и JSON с ответом
     */
    @ExceptionHandler(EntityExistsException.class)
    public ResponseEntity<?> handleEntityExistingException(EntityExistsException exception, WebRequest request) {
        log.info("EntityExistsException: {}", exception.getMessage());
        log.debug(exception.getMessage(), exception);

        return new ResponseEntity<>(
                new ErrorResponse()
                        .setStatus(CONFLICT)
                        .setUrl(getUrl(request)),
                CONFLICT
        );
    }
}