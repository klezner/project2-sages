package com.sages.project2.domain.exceptions;

public class BranchDoesNotExistException extends RuntimeException {
    public BranchDoesNotExistException() {
        super("Branch does not exist");
    }
}
