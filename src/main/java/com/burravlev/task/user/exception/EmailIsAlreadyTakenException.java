package com.burravlev.task.user.exception;

public class EmailIsAlreadyTakenException extends RuntimeException {
    public EmailIsAlreadyTakenException(String message) {
        super(message);
    }
}
