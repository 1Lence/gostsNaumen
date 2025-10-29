package com.example.gostsNaumen.handler;

import com.example.gostsNaumen.handler.dto.ValidationError;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Дто для красивого отображения ошибки.
 */
public class ErrorResponse {
    private LocalDateTime timestamp;
    private HttpStatus status;
    private String message;
    private String url;
    private List<ValidationError> validationErrors;

    public ErrorResponse(LocalDateTime timestamp, HttpStatus status, String message, String url, List<ValidationError> validationErrors) {
        this.timestamp = timestamp;
        this.status = status;
        this.message = message;
        this.url = url;
        this.validationErrors = validationErrors;
    }

    public ErrorResponse() {
    }

    public ErrorResponse setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
        return this;
    }

    public ErrorResponse setStatus(HttpStatus status) {
        this.status = status;
        return this;
    }

    public ErrorResponse setMessage(String message) {
        this.message = message;
        return this;
    }

    public ErrorResponse setUrl(String url) {
        this.url = url;
        return this;
    }

    public ErrorResponse setValidationErrors(List<ValidationError> validationErrors) {
        this.validationErrors = validationErrors;
        return this;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public String getUrl() {
        return url;
    }

    public List<ValidationError> getValidationErrors() {
        return validationErrors;
    }
}