package com.music_streaming.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public String handleUnauthorizedAccess(RuntimeException ex) {
        return "Unauthorized: " + ex.getMessage();
    }
}

