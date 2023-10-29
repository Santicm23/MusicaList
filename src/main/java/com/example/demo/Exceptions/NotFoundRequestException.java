package com.example.demo.Exceptions;

public class NotFoundRequestException extends StandardRequestException {
    public NotFoundRequestException(String mensaje) {
        super(mensaje);
    }
}
