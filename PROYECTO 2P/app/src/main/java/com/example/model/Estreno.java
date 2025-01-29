package com.example.model;

/**
 * Clase que representa un estreno de película.
 * Contiene los detalles de un estreno, incluyendo el ID de la película, el título y la fecha de estreno.
 */
public class Estreno implements Comparable<Estreno> {

    private int idPelicula;
    private String titulo;
    private String fecha;

    /**
     * Constructor de la clase Estreno.
     *
     * @param idPelicula El ID único de la película.
     * @param titulo El título de la película.
     * @param fecha La fecha de estreno de la película.
     */
    public Estreno(int idPelicula, String titulo, String fecha) {
        this.idPelicula = idPelicula;
        this.titulo = titulo;
        this.fecha = fecha;
    }

    /**
     * Obtiene el ID de la película.
     *
     * @return El ID de la película.
     */
    public int getIdPelicula() {
        return idPelicula;
    }

    /**
     * Establece el ID de la película.
     *
     * @param idPelicula El nuevo ID de la película.
     */
    public void setIdPelicula(int idPelicula) {
        this.idPelicula = idPelicula;
    }

    /**
     * Obtiene el título de la película.
     *
     * @return El título de la película.
     */
    public String getTitulo() {
        return titulo;
    }

    /**
     * Establece el título de la película.
     *
     * @param titulo El nuevo título de la película.
     */
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    /**
     * Obtiene la fecha de estreno de la película.
     *
     * @return La fecha de estreno de la película.
     */
    public String getFecha() {
        return fecha;
    }

    /**
     * Establece la fecha de estreno de la película.
     *
     * @param fecha La nueva fecha de estreno de la película.
     */
    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    /**
     * Implementación del método compareTo para comparar por el título de la película.
     *
     * @param otroEstreno El objeto Estreno con el que se va a comparar.
     * @return Un valor entero que representa la comparación (ascendente por título).
     */
    @Override
    public int compareTo(Estreno otroEstreno) {
        return this.titulo.compareTo(otroEstreno.titulo);
    }

    /**
     * Devuelve una representación en cadena del objeto Estreno.
     *
     * @return Una cadena que describe el estreno.
     */
    @Override
    public String toString() {
        return "Estrenos{" +
                "idPelicula=" + idPelicula +
                ", titulo='" + titulo + '\'' +
                ", fecha='" + fecha + '\'' +
                '}';
    }
}
