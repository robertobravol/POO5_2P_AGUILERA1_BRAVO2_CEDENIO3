package com.example.model;
import java.io.Serializable;

public class Pago implements Serializable {
    private int idPago;
    private int idFuncion;
    private String nombre;
    private String numeroTarjeta;
    private String tipoTarjeta;
    private int numeroEntradas;
    private double totalPagar;

    public Pago(int idPago, int idFuncion, String nombre, String numeroTarjeta, String tipoTarjeta, int numeroEntradas, double totalPagar) {
        this.idPago = idPago;
        this.idFuncion = idFuncion;
        this.nombre = nombre;
        this.numeroTarjeta = numeroTarjeta;
        this.tipoTarjeta = tipoTarjeta;
        this.numeroEntradas = numeroEntradas;
        this.totalPagar = totalPagar;
    }



    @Override
    public String toString() {
        return idPago + "," + idFuncion + "," + nombre + "," + numeroTarjeta + "," + tipoTarjeta + "," + numeroEntradas + "," + totalPagar;
    }


}
