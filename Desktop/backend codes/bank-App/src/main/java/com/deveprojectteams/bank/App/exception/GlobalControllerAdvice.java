package com.deveprojectteams.bank.App.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.Objects;

@RestControllerAdvice
public class GlobalControllerAdvice {

    private static final Logger logger = LoggerFactory.getLogger(GlobalControllerAdvice.class);

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        StringBuilder errorMessage = new StringBuilder("Validation errors:");
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errorMessage.append("\n  - Field: ").append(error.getField())
                    .append(", Rejected value: '").append(error.getRejectedValue())
                    .append("', Reason: ").append(error.getDefaultMessage());
        }
        logger.error("Validation error: {}", errorMessage.toString(), ex);
        return new ResponseEntity<>(errorMessage.toString(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<String> handleTypeMismatchException(MethodArgumentTypeMismatchException ex) {
        String message = "Invalid value for parameter: " + ex.getName() + ". Expected type: " +
                Objects.requireNonNull(ex.getRequiredType()).getName();
        logger.warn("Type mismatch for parameter: {}", ex.getName(), ex);
        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<String> handleUnexpectedExceptions(Exception ex) {
        String message = "An unexpected error occurred: " + ex.getMessage();
        logger.error("Unexpected exception: {}", message, ex);
        return new ResponseEntity<>(message, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
