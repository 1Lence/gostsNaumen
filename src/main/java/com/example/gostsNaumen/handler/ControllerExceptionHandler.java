package com.example.gostsNaumen.handler;

import com.example.gostsNaumen.exception.BusinessException;
import com.example.gostsNaumen.handler.dto.ValidationError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Централизованный обработчик ошибок контроллера.
 * Здесь необходимо отлавливать ошибки связанные с валидацией/внешней работой приложения.
 */
@ControllerAdvice
public class ControllerExceptionHandler extends BaseControllerAdvice {
    private final Logger log = LoggerFactory.getLogger(ControllerExceptionHandler.class);

    /**
     * Основной обработчик валидации.
     * Отлавливает и обрабатывает все ошибки связанные с входящей валидацией данных.
     *
     * @param exception ошибка валидации
     * @param request   данные HTTP запроса
     * @return удобочитаемый JSON с описанием ошибки
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleDtoValidatingException(MethodArgumentNotValidException exception,
                                                                      WebRequest request) {
        log.info("MethodArgumentNotValidException: {}", exception.getMessage());
        log.debug(exception.getMessage(), exception);

        List<ValidationError> validationErrors = exception.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(fields -> new ValidationError(
                                fields.getField(),
                                fields.getDefaultMessage()
                        )
                )
                .toList();

        String url = getUrl(request);

        return new ResponseEntity<>(
                new ErrorResponse()
                        .setStatus(HttpStatus.BAD_REQUEST)
                        .setUrl(url)
                        .setTimestamp(LocalDateTime.now())
                        .setValidationErrors(validationErrors),
                HttpStatus.BAD_REQUEST
        );
    }

    /**
     * Обработка ошибок бизнес-логики приложения
     *
     * @param exception возникает при ошибках в бизнес-логике
     * @param request   данные HTTP запроса
     * @return удобочитаемый JSON с описанием ошибки
     */
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponse> handleBusinessException(final BusinessException exception, WebRequest request) {
        log.info("BusinessException: {}", exception.getMessage());
        log.debug(exception.getMessage(), exception);

        return new ResponseEntity<>(
                new ErrorResponse()
                        .setTimestamp(LocalDateTime.now())
                        .setMessage(exception.getFormattedMessage())
                        .setStatus(exception.getErrorCode().getStatus())
                        .setUrl(getUrl(request)),
                exception.getErrorCode().getStatus()
        );
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentTypeMismatchException(
            final MethodArgumentTypeMismatchException exception,
            WebRequest request) {
        log.info("Method Argument Type Mismatch Exception: {}", exception.getMessage());
        log.debug(exception.getMessage(), exception);

        return new ResponseEntity<>(
                new ErrorResponse()
                        .setTimestamp(LocalDateTime.now())
                        .setMessage("Некорректный аргумент: %s".formatted(exception.getValue()))
                        .setStatus(HttpStatus.BAD_REQUEST)
                        .setUrl(getUrl(request)),
                HttpStatus.BAD_REQUEST
        );
    }
}