package com.infy.qrcode.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = CustomException.class)
    public ResponseEntity<Object> customExceceptionHandler(CustomException exception) {

        return ErrorHandler.generateResponse(exception.getMessage(),exception.getResponseId(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<Object> genericHandler(CustomException exception) {

        return ErrorHandler.generateResponse(exception.getMessage(),exception.getResponseId(), HttpStatus.NOT_FOUND);
    }
}