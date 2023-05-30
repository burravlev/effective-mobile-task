package com.burravlev.task.api;

import com.burravlev.task.api.dto.ErrorDto;
import com.burravlev.task.exception.NotFoundException;
import com.burravlev.task.exception.UnauthenticatedException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;
import java.util.stream.Collectors;


@ControllerAdvice
public class ApiExceptionHandler {
    @ExceptionHandler(DuplicateKeyException.class)
    public ResponseEntity<ErrorDto> handleDataIntegrityViolationException(DuplicateKeyException e) {
        return new ResponseEntity<>(new ErrorDto(e.getMessage()), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorDto> handleIllegalArgumentException(IllegalArgumentException e) {
        return new ResponseEntity<>(new ErrorDto(e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorDto> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        List<FieldError> error = e.getBindingResult().getFieldErrors();
        return new ResponseEntity<>(new ErrorDto(error.stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.toList())), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorDto> handleNotFoundException(Exception e) {
        String message = e.getMessage();
        return new ResponseEntity<>(new ErrorDto(message), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UnauthenticatedException.class)
    public ResponseEntity<ErrorDto> handleUnauthenticatedException(UnauthenticatedException e) {
        return new ResponseEntity<>(new ErrorDto(e.getMessage()), HttpStatus.NOT_FOUND);
    }
}
