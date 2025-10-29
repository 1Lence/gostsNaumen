package com.example.gostsNaumen.handler;

import com.example.gostsNaumen.handler.dto.ValidationError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.List;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

/**
 * Централизованный обработчик ошибок.
 * Здесь необходимо отлавливать ошибки связанные с валидацией/внешней работой приложения.
 */
@ControllerAdvice
public class ControllerExceptionHandler extends BaseControllerAdvice {
    private final Logger log = LoggerFactory.getLogger(ControllerExceptionHandler.class);

    /**
     * Основной обработчик валидации.
     * Отлавливает и обрабатывает все ошибки связанные с входящей валидацией данных.
     * @param exception ошибка валидации
     * @param request данные HTTP запроса
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
                        .setStatus(BAD_REQUEST)
                        .setUrl(url)
                        .setValidationErrors(validationErrors),
                BAD_REQUEST
        );
    }
}