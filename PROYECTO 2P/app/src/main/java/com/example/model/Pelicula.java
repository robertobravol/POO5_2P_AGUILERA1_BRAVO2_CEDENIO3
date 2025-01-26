package com.example.model;

import java.io.Serializable;

/**
 * Clase que representa una película.
 * Contiene detalles sobre la película, como su ID, título, duración y el nombre del archivo de la imagen asociada.
 */
public class Pelicula implements Serializable {

    private int peliculaID;
    private String titulo;
    private String duracion;
    private String nombreArchivo;

    /**
     * Constructor de la clase Pelicula.
     *
     * @param peliculaID El ID único de la película.
     * @param titulo El título de la película.
     * @param duracion La duración de la película en formato de tiempo (por ejemplo, "120 min").
     * @param nombreArchivo El nombre del archivo de la imagen asociada a la película.
     */
    public Pelicula(int peliculaID, String titulo, String duracion, String nombreArchivo) {
        this.peliculaID = peliculaID;
        this.titulo = titulo;
        this.duracion = duracion;
        this.nombreArchivo = nombreArchivo;
    }

    /**
     * Obtiene el ID de la película.
     *
     * @return El ID de la película.
     */
    public int getPeliculaID() {
        return peliculaID;
    }

    /**
     * Establece el ID de la película.
     *
     * @param idPelicula El nuevo ID de la película.
     */
    public void setPeliculaID(int idPelicula) {
        this.peliculaID = idPelicula;
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
     * Obtiene la duración de la película.
     *
     * @return La duración de la película (por ejemplo, "120 min").
     */
    public String getDuracion() {
        return duracion;
    }

    /**
     * Establece la duración de la película.
     *
     * @param duracion La nueva duración de la película.
     */
    public void setDuracion(String duracion) {
        this.duracion = duracion;
    }

    /**
     * Obtiene el nombre del archivo de la imagen asociada a la película.
     *
     * @return El nombre del archivo de la imagen.
     */
    public String getNombreArchivo() {
        return nombreArchivo;
    }

    /**
     * Establece el nombre del archivo de la imagen asociada a la película.
     *
     * @param nombreArchivo El nuevo nombre del archivo de la imagen.
     */
    public void setNombreArchivo(String nombreArchivo) {
        this.nombreArchivo = nombreArchivo;
    }
}
