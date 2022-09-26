package com.wellbeignatwork.backend.exceptions.Evaluation;

public class ApiConflictException extends RuntimeException {
    public ApiConflictException(String message) {
        super(message);
    }
}
