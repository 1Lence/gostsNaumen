package com.example.gostsNaumen.handler;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

import static org.springframework.http.HttpStatus.*;

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
    public ResponseEntity<ErrorResponse> handleEntityNotFoundException(EntityNotFoundException exception, WebRequest request) {
        log.info("EntityNotFoundException: {}", exception.getMessage());
        log.debug(exception.getMessage(), exception);

        return new ResponseEntity<>(
                new ErrorResponse()
                        .setTimestamp(LocalDateTime.now())
                        .setMessage(exception.getMessage())
                        .setStatus(NOT_FOUND)
                        .setUrl(getUrl(request)),
                NOT_FOUND
        );
    }

    /**
     * Отлавливает ошибки связанные с неверно переданными аргументами в метод
     *
     * @param exception возникает при передачи неверных параметров в метод
     * @param request   http запрос
     * @return HTTP Status код и JSON с ответом
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgument(IllegalArgumentException exception, WebRequest request) {
        log.info("IllegalArgumentException: {}", exception.getMessage());
        log.debug(exception.getMessage(), exception);

        return new ResponseEntity<>(
                new ErrorResponse()
                        .setTimestamp(LocalDateTime.now())
                        .setMessage(exception.getMessage())
                        .setStatus(BAD_REQUEST)
                        .setUrl(getUrl(request)),
                BAD_REQUEST
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
                        .setTimestamp(LocalDateTime.now())
                        .setMessage(exception.getMessage())
                        .setStatus(CONFLICT)
                        .setUrl(getUrl(request)),
                CONFLICT
        );
    }
}