package com.garcia.adrian.triviaapp.model;

public class ModoHistorial extends EstiloJuegoBase{

    private String puntuacion;          // Para historial
    private String acertadas;           // Para historial
    private String categoria;           // Para historial
    private String fecha;               // Para historial

    // Constructor para
    public ModoHistorial(String titulo, String puntuacion, String acertadas, String categoria, String fecha) {
        super(titulo);
        this.puntuacion = puntuacion;
        this.acertadas = acertadas;
        this.categoria = categoria;
        this.fecha = fecha;
    }

    public String getPuntuacion() {
        return puntuacion;
    }

    public void setPuntuacion(String puntuacion) {
        this.puntuacion = puntuacion;
    }

    public String getAcertadas() {
        return acertadas;
    }

    public void setAcertadas(String acertadas) {
        this.acertadas = acertadas;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
}
