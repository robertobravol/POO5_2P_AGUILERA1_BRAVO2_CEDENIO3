package com.example.model;

/**
 * Clase que representa una función de película.
 * Contiene los detalles de una función programada, como el ID de la función, el ID de la película, la fecha, la hora y la sala.
 */
public class Funcion {

    private int idFuncion;
    private int idPelicula;
    private String fecha;
    private String hora;
    private String sala;

    /**
     * Constructor de la clase Funcion.
     *
     * @param idFuncion El ID único de la función.
     * @param idPelicula El ID de la película asociada a la función.
     * @param fecha La fecha en que se realiza la función.
     * @param hora La hora en que se realiza la función.
     * @param sala La sala donde se proyecta la película.
     */
    public Funcion(int idFuncion, int idPelicula, String fecha, String hora, String sala) {
        this.idFuncion = idFuncion;
        this.idPelicula = idPelicula;
        this.fecha = fecha;
        this.hora = hora;
        this.sala = sala;
    }

    /**
     * Obtiene el ID de la función.
     *
     * @return El ID de la función.
     */
    public int getIdFuncion() {
        return idFuncion;
    }

    /**
     * Establece el ID de la función.
     *
     * @param idFuncion El nuevo ID de la función.
     */
    public void setIdFuncion(int idFuncion) {
        this.idFuncion = idFuncion;
    }

    /**
     * Obtiene el ID de la película asociada a la función.
     *
     * @return El ID de la película.
     */
    public int getIdPelicula() {
        return idPelicula;
    }

    /**
     * Establece el ID de la película asociada a la función.
     *
     * @param idPelicula El nuevo ID de la película.
     */
    public void setIdPelicula(int idPelicula) {
        this.idPelicula = idPelicula;
    }

    /**
     * Obtiene la fecha en la que se realiza la función.
     *
     * @return La fecha de la función.
     */
    public String getFecha() {
        return fecha;
    }

    /**
     * Establece la fecha en la que se realiza la función.
     *
     * @param fecha La nueva fecha de la función.
     */
    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    /**
     * Obtiene la sala donde se proyecta la película.
     *
     * @return El nombre de la sala.
     */
    public String getSala() {
        return sala;
    }

    /**
     * Establece la sala donde se proyecta la película.
     *
     * @param sala El nombre de la sala.
     */
    public void setSala(String sala) {
        this.sala = sala;
    }

    /**
     * Obtiene la hora en la que se realiza la función.
     *
     * @return La hora de la función.
     */
    public String getHora() {
        return hora;
    }

    /**
     * Establece la hora en la que se realiza la función.
     *
     * @param hora La nueva hora de la función.
     */
    public void setHora(String hora) {
        this.hora = hora;
    }

    /**
     * Devuelve una representación en cadena de la función, mostrando la hora y la sala.
     *
     * @return Una cadena que describe la función (hora y sala).
     */
    @Override
    public String toString() {
        return  "Hora: " + hora + ", sala: " + sala;
    }
}
