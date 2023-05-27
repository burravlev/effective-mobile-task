package com.burravlev.task.user.controller;

import com.burravlev.task.api.dto.ErrorDto;
import com.burravlev.task.user.exception.EmailIsAlreadyTakenException;
import com.burravlev.task.user.exception.UserNotFoundException;
import com.burravlev.task.user.exception.UsernameIsAlreadyTakenException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class UserExceptionHandler {
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorDto> handleUserNotFoundException(UserNotFoundException e) {
        return new ResponseEntity<>(new ErrorDto(e.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UsernameIsAlreadyTakenException.class)
    public ResponseEntity<ErrorDto> handleUsernameIsAlreadyTakenException(UsernameIsAlreadyTakenException e) {
        return new ResponseEntity<>(new ErrorDto(e.getMessage()), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(EmailIsAlreadyTakenException.class)
    public ResponseEntity<ErrorDto> handleEmailIsAlreadyTakenException(EmailIsAlreadyTakenException e) {
        return new ResponseEntity<>(new ErrorDto(e.getMessage()), HttpStatus.CONFLICT);
    }
}
