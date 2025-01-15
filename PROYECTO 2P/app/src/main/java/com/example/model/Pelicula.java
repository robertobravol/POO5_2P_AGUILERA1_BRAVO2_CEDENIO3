package com.example.model;

import java.io.Serializable;

public class Pelicula implements Serializable {
    private int peliculaID;
    private String titulo;
    private String duracion;
    private String nombreArchivo;


    public Pelicula(int peliculaID, String titulo, String duracion, String nombreArchivo){
        this.peliculaID = peliculaID;
        this.titulo = titulo;
        this.duracion = duracion;
        this.nombreArchivo = nombreArchivo;
    }


    public int getPeliculaID() {
        return peliculaID;
    }

    public void setPeliculaID(int idPelicula) {
        this.peliculaID = idPelicula;
    }


    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDuracion() {
        return duracion;
    }

    public void setDuracion(String duracion) {
        this.duracion = duracion;
    }
    public String getNombreArchivo() {
        return nombreArchivo;
    }

    public void setNombreArchivo(String nombreArchivo) {
        this.nombreArchivo = nombreArchivo;
    }
}
