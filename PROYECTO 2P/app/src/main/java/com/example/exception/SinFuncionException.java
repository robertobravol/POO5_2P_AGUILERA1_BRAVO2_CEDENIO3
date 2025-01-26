package com.example.exception;

/**
 * Excepción que se lanza cuando no hay funciones disponibles en la fecha seleccionada.
 * Esta excepción es útil para manejar situaciones en las que el usuario intenta ver funciones en un día sin funciones programadas.
 */
public class SinFuncionException extends RuntimeException {

    /**
     * Constructor de la excepción. Inicializa el mensaje de error predeterminado.
     */
    public SinFuncionException() {
        // Llamar al constructor de la clase base (RuntimeException) con un mensaje personalizado
        super("No hay funciones en la fecha seleccionada.");
    }
}
