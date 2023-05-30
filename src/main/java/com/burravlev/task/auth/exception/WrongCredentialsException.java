package com.burravlev.task.auth.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class WrongCredentialsException extends RuntimeException {
    public WrongCredentialsException(String message) {
        super(message);
    }
}
