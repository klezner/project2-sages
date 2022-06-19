package com.sages.project2.domain.exceptions;

public class BranchAlreadyExistsException extends RuntimeException {
    public BranchAlreadyExistsException() {
        super("Branch already exists.");
    }
}
