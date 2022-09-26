package com.wellbeignatwork.backend.exceptions.Forum;

public class PostException extends RuntimeException {
    public PostException(final String postNotFound) {
        super(postNotFound);
    }
}
