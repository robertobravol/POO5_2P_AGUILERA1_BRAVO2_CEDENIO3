package com.example.model;
import java.io.Serializable;

/**
 * Clase que representa un pago realizado por un usuario para una función de cine.
 * Contiene detalles sobre el pago, como el ID del pago, ID de la función, nombre del titular de la tarjeta, número de tarjeta,
 * tipo de tarjeta, total a pagar y número de entradas.
 */
public class Pago implements Serializable {
    private int idPago;
    private int idFuncion;
    private String nombre;
    private String numeroTarjeta;
    private String tipo;
    private double totalPagar;
    private int numeroEntradas;

    /**
     * Constructor de la clase Pago.
     *
     * @param idPago El ID único del pago.
     * @param idFuncion El ID de la función para la que se realizó el pago.
     * @param nombre El nombre del titular de la tarjeta.
     * @param numeroTarjeta El número de la tarjeta utilizada para el pago.
     * @param tipo El tipo de tarjeta (por ejemplo, Visa, MasterCard).
     * @param numeroEntradas El número de entradas compradas.
     * @param totalPagar El monto total a pagar por el usuario.
     */
    public Pago(int idPago, int idFuncion, String nombre, String numeroTarjeta, String tipo, int numeroEntradas, double totalPagar) {
        this.idPago = idPago;
        this.idFuncion = idFuncion;
        this.nombre = nombre;
        this.numeroTarjeta = numeroTarjeta;
        this.tipo = tipo;
        this.numeroEntradas = numeroEntradas;
        this.totalPagar = totalPagar;
    }

    /**
     * Devuelve una representación en cadena de los detalles del pago.
     * La representación está separada por comas.
     *
     * @return Una cadena con los detalles del pago.
     */
    @Override
    public String toString() {
        return idPago + "," + idFuncion + "," + nombre + "," + numeroTarjeta + "," + tipo + "," + numeroEntradas + "," + totalPagar;
    }

    /**
     * Obtiene el nombre del titular de la tarjeta.
     *
     * @return El nombre del titular de la tarjeta.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece el nombre del titular de la tarjeta.
     *
     * @param nombre El nuevo nombre del titular de la tarjeta.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtiene el ID de la función asociada con el pago.
     *
     * @return El ID de la función.
     */
    public int getIdFuncion() {
        return idFuncion;
    }

    /**
     * Establece el ID de la función asociada con el pago.
     *
     * @param idFuncion El nuevo ID de la función.
     */
    public void setIdFuncion(int idFuncion) {
        this.idFuncion = idFuncion;
    }

    /**
     * Obtiene el ID del pago.
     *
     * @return El ID del pago.
     */
    public int getIdPago() {
        return idPago;
    }

    /**
     * Establece el ID del pago.
     *
     * @param idPago El nuevo ID del pago.
     */
    public void setIdPago(int idPago) {
        this.idPago = idPago;
    }

    /**
     * Obtiene el número de tarjeta utilizada para el pago.
     *
     * @return El número de la tarjeta.
     */
    public String getNumeroTarjeta() {
        return numeroTarjeta;
    }

    /**
     * Establece el número de tarjeta utilizada para el pago.
     *
     * @param numeroTarjeta El nuevo número de la tarjeta.
     */
    public void setNumeroTarjeta(String numeroTarjeta) {
        this.numeroTarjeta = numeroTarjeta;
    }

    /**
     * Obtiene el tipo de tarjeta utilizada para el pago.
     *
     * @return El tipo de la tarjeta (Visa, MasterCard, etc.).
     */
    public String getTipo() {
        return tipo;
    }

    /**
     * Establece el tipo de tarjeta utilizada para el pago.
     *
     * @param tipo El nuevo tipo de tarjeta.
     */
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    /**
     * Obtiene el monto total a pagar por el usuario.
     *
     * @return El total a pagar.
     */
    public double getTotalPagar() {
        return totalPagar;
    }

    /**
     * Establece el monto total a pagar por el usuario.
     *
     * @param totalPagar El nuevo monto total a pagar.
     */
    public void setTotalPagar(double totalPagar) {
        this.totalPagar = totalPagar;
    }

    /**
     * Obtiene el número de entradas compradas.
     *
     * @return El número de entradas.
     */
    public int getNumeroEntradas() {
        return numeroEntradas;
    }

    /**
     * Establece el número de entradas compradas.
     *
     * @param numeroEntradas El nuevo número de entradas.
     */
    public void setNumeroEntradas(int numeroEntradas) {
        this.numeroEntradas = numeroEntradas;
    }
}