package com.sages.project2.domain.exceptions;

public class RepositoryDoesNotExistException extends RuntimeException {
    public RepositoryDoesNotExistException() {
        super("Repository does not exist.");
    }
}
