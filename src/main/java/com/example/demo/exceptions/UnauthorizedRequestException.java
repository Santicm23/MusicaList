package com.example.demo.exceptions;

public class UnauthorizedRequestException extends StandardRequestException {
    public UnauthorizedRequestException(String message) {
        super(message);
    }
}
