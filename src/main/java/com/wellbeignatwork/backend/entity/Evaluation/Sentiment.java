package com.wellbeignatwork.backend.entity.Evaluation;

public enum Sentiment {
    Positive("Positive"),
    Negative("Negative"),
    Neutral("Neutral"),
    
    Very_negative("Very negative");

    Sentiment(String displayName) {
        this.displayName = displayName;
    }

    private String displayName;



    public String displayName( ) { return displayName; }

    // Optionally and/or additionally, toString.
    @Override
    public String toString() { return displayName; }

}
