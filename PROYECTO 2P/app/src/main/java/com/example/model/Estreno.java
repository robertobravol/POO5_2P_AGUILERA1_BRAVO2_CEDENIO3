package com.example.model;

public class Estreno {
    private int idPelicula;
    private String titulo;
    private String fecha;

    // Constructor
    public Estreno(int idPelicula, String titulo, String fecha) {
        this.idPelicula = idPelicula;
        this.titulo = titulo;
        this.fecha = fecha;
    }

    // Getters y Setters
    public int getIdPelicula() {
        return idPelicula;
    }

    public void setIdPelicula(int idPelicula) {
        this.idPelicula = idPelicula;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }


    @Override
    public String toString() {
        return "Estrenos{" +
                "idPelicula=" + idPelicula +
                ", titulo='" + titulo + '\'' +
                ", fecha='" + fecha + '\'' +
                '}';
    }
}
