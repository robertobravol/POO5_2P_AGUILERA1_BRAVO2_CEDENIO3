package com.example.exception;

public class SinFuncionException extends RuntimeException {
    public SinFuncionException() {

        super("No hay funciones en la fecha seleccionada.");
    }
}
