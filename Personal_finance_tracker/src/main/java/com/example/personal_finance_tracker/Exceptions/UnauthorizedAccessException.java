package com.example.personal_finance_tracker.Exceptions;

public class UnauthorizedAccessException extends RuntimeException{

    private static final long serialVersionUID = 1L;

    public UnauthorizedAccessException(String message) {
        super(message);
    }
}
