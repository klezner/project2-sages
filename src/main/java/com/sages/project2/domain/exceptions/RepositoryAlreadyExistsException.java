package com.sages.project2.domain.exceptions;

public class RepositoryAlreadyExistsException extends RuntimeException {
    public RepositoryAlreadyExistsException() {
        super("Repository already exists.");
    }
}
