package com.rsakin.restapidesign.util;

import com.rsakin.restapidesign.model.exception.MissingFieldsException;
import com.rsakin.restapidesign.model.exception.TaskNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(TaskNotFoundException.class)
    protected ResponseEntity<Object> handleTaskNotFound(TaskNotFoundException ex, WebRequest request) {
        ApiError apiError = new ApiError(HttpStatus.NOT_FOUND, ex.getMessage());
        return handleExceptionInternal(ex, apiError, new HttpHeaders(), apiError.status(), request);
    }

    @ExceptionHandler(MissingFieldsException.class)
    protected ResponseEntity<Object> handleMissingFields(MissingFieldsException ex, WebRequest request) {
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, ex.getMessage());
        return handleExceptionInternal(ex, apiError, new HttpHeaders(), apiError.status(), request);
    }

    record ApiError(HttpStatus status, String message) {
    }

}