package com.example.ledger.exception;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * Centralized exception handler for the Ledger API.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles insufficient balance errors during withdrawal attempts.
     */
    @ExceptionHandler(InsufficientBalanceException.class)
    public ResponseEntity<Object> handleInsufficientBalance(InsufficientBalanceException ex) {
        return buildErrorResponse("Insufficient Balance", ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles wrong Transaction Types and wrong formatted JSON requests
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex) {
        Throwable mostSpecificCause = ex.getMostSpecificCause();

        if (mostSpecificCause instanceof InvalidFormatException formatException) {
            String fieldName = formatException.getPath().isEmpty() ? "unknown" : formatException.getPath().get(0).getFieldName();
            String message = String.format("Invalid value for field '%s': '%s'", fieldName, formatException.getValue());
            return buildErrorResponse("Invalid Format", message, HttpStatus.BAD_REQUEST);
        }

        // Fallback for other JSON parsing issues
        return buildErrorResponse("Malformed JSON request", mostSpecificCause.getMessage(), HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles validation errors from @Valid annotated DTOs.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleValidationErrors(MethodArgumentNotValidException ex) {
        var errors = ex.getBindingResult().getFieldErrors().stream()
                .map(e -> e.getField() + ": " + e.getDefaultMessage())
                .toList();
        return buildErrorResponse("Validation Failed", errors.toString(), HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles invalid arguments (e.g., negative or zero amounts).
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Object> handleIllegalArgument(IllegalArgumentException ex) {
        return buildErrorResponse("Invalid Request", ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    /**
     * Fallback for any uncaught exceptions.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleGenericException(Exception ex) {
        return buildErrorResponse("Internal Server Error", "An unexpected error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Reusable helper for building consistent error responses.
     */
    private ResponseEntity<Object> buildErrorResponse(String error, String message, HttpStatus status) {
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("error", error);
        body.put("message", message);
        body.put("status", status.value());
        return new ResponseEntity<>(body, status);
    }
}
