package com.example.demo.exceptions;

public class ForbiddenRequestException extends StandardRequestException {
    public ForbiddenRequestException(String message) {
        super(message);
    }
}
