package com.example.model;
import java.io.Serializable;

public class Pago implements Serializable {
    private int idPago;
    private int idFuncion;
    private String nombre;
    private String numeroTarjeta;
    private String tipo;
    private double totalPagar;
    private int numeroEntradas;

    public Pago(int idPago, int idFuncion, String nombre, String numeroTarjeta, String tipo, int numeroEntradas, double totalPagar) {
        this.idPago = idPago;
        this.idFuncion = idFuncion;
        this.nombre = nombre;
        this.numeroTarjeta = numeroTarjeta;
        this.tipo = tipo;
        this.numeroEntradas = numeroEntradas;
        this.totalPagar = totalPagar;
    }

    @Override
    public String toString() {
        return idPago + "," + idFuncion + "," + nombre + "," + numeroTarjeta + "," + tipo + "," + numeroEntradas + "," + totalPagar;
    }

    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public int getIdFuncion() {
        return idFuncion;
    }
    public void setIdFuncion(int idFuncion) {
        this.idFuncion = idFuncion;
    }
    public int getIdPago() {
        return idPago;
    }
    public void setIdPago(int idPago) {
        this.idPago = idPago;
    }
    public String getNumeroTarjeta() {
        return numeroTarjeta;
    }
    public void setNumeroTarjeta(String numeroTarjeta) {
        this.numeroTarjeta = numeroTarjeta;
    }
    public String getTipo() {
        return tipo;
    }
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
    public double getTotalPagar() {
        return totalPagar;
    }
    public void setTotalPagar(double totalPagar) {
        this.totalPagar = totalPagar;
    }
    public int getNumeroEntradas() {
        return numeroEntradas;
    }
    public void setNumeroEntradas(int numeroEntradas) {
        this.numeroEntradas = numeroEntradas;
    }

}
