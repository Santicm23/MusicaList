package com.example.demo.exceptions;

public class NotFoundRequestException extends StandardRequestException {
    public NotFoundRequestException(String mensaje) {
        super(mensaje);
    }
}
