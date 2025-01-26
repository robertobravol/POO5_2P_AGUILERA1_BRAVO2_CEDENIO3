package com.example.exception;

/**
 * Excepción que se lanza cuando los datos proporcionados son incompletos.
 * Esta excepción es útil para validar la integridad de la información ingresada por el usuario.
 */
public class DatosIncompletosException extends Exception {

    /**
     * Constructor de la excepción. Inicializa la excepción con el mensaje de error proporcionado.
     *
     * @param message El mensaje de error que describe la causa de la excepción.
     */
    public DatosIncompletosException(String message) {
        // Llamar al constructor de la clase base (Exception) con el mensaje proporcionado
        super(message);
    }
}

