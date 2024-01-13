package com.rsakin.restapidesign.model.exception;

public class MissingFieldsException extends RuntimeException {

    public MissingFieldsException(String message) {
        super(message);
    }
}

