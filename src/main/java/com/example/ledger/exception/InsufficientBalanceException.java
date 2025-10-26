package com.example.ledger.exception;

/**
 * Custom exception thrown when a withdrawal is attempted
 * but the account balance is insufficient to fulfill the request.
 *
 * This exception is handled in GlobalExceptionHandler to return
 * a 400 Bad Request response to the client.
 */
public class InsufficientBalanceException extends RuntimeException {
    public InsufficientBalanceException(String message) {
        super(message);
    }
}
