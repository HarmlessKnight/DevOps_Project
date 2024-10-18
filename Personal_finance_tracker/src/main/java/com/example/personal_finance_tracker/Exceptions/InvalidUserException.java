package com.example.personal_finance_tracker.Exceptions;

public class InvalidUserException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public InvalidUserException(String message) {
        super(message);
    }
}