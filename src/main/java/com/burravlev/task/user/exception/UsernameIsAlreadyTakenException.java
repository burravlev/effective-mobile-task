package com.burravlev.task.user.exception;

public class UsernameIsAlreadyTakenException extends RuntimeException {
    public UsernameIsAlreadyTakenException(String message) {
        super(message);
    }
}
