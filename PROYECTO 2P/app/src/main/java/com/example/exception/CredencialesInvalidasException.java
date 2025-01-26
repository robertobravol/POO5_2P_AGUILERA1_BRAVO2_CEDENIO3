package com.example.exception;

/**
 * Excepción que se lanza cuando las credenciales de inicio de sesión son inválidas.
 * Esta excepción es lanzada si el usuario o la contraseña ingresados son incorrectos.
 */
public class CredencialesInvalidasException extends RuntimeException {

    /**
     * Constructor de la excepción. Inicializa el mensaje de error predeterminado.
     */
    public CredencialesInvalidasException() {
        // Llamar al constructor de la clase base (RuntimeException) con un mensaje personalizado
        super("El usuario o la contraseña son incorrectas.");
    }
}
