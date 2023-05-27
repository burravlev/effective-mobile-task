package com.burravlev.task.api;

import com.burravlev.task.api.dto.ErrorDto;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ApiExceptionHandler {
    @ExceptionHandler(DuplicateKeyException.class)
    public ResponseEntity<ErrorDto> handleDataIntegrityViolationException(DuplicateKeyException e) {
        return new ResponseEntity<>(new ErrorDto(e.getMessage()), HttpStatus.CONFLICT);
    }
}
