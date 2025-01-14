package com.example.model;

public class Pelicula {
    private int peliculaID;
    private String titulo;
    private int duracion;


    public Pelicula(int peliculaID, String titulo, int duracion){
        this.peliculaID = peliculaID;
        this.titulo = titulo;
        this.duracion = duracion;
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

    public double getDuracion() {
        return duracion;
    }

    public void setDuracion(int duracion) {
        this.duracion = duracion;
    }
}
