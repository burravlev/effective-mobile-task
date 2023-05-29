package com.burravlev.task.content.controller;

import com.burravlev.task.api.dto.ErrorDto;
import com.burravlev.task.content.exception.FileProcessingException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class FileControllerExceptionHandler {
    @ExceptionHandler(FileProcessingException.class)
    public ResponseEntity<ErrorDto> handleFileProcessingException(FileProcessingException e) {
        return null;
    }
}
