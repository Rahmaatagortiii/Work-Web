package com.wellbeignatwork.backend.exceptions.Evaluation;

public class SurveyNotFoundException extends RuntimeException {
    public SurveyNotFoundException(String message) {
        super(message);
    }
}
