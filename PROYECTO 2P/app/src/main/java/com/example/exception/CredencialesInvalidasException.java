package com.example.exception;

public class CredencialesInvalidasException extends RuntimeException {
    public CredencialesInvalidasException() {
        super("El usuario o la contraseña son incorrectas.");
    }
}
