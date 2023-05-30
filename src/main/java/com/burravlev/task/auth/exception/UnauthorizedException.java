package com.burravlev.task.auth.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class UnauthorizedException extends RuntimeException {
    public UnauthorizedException(String message) {
        super(message);
    }

    public UnauthorizedException(Exception e) {
        super(e);
    }
}
