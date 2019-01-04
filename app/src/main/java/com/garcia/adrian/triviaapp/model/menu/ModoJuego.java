package com.garcia.adrian.triviaapp.model.menu;

import com.garcia.adrian.triviaapp.enums.CATEGORIA;
import com.garcia.adrian.triviaapp.enums.DIFICULTAD;

import java.io.Serializable;

public class ModoJuego implements Serializable {

    private String titulo;
    private String maximaPuntuacion;    // Para categoria
    private String totalAcertadas;      // Para categoria
    private CATEGORIA categoria;

    private DIFICULTAD dificultad;
    private String descripcion;         // Para modoJuego
    private boolean activado;           // Para modoJuego
    private int numeroPreguntas;        // Para modoJuego


    public ModoJuego(String titulo, CATEGORIA categoria, String maximaPuntuacion, String totalAcertadas) {
        this.titulo = titulo;
        this.categoria = categoria;
        this.maximaPuntuacion = maximaPuntuacion;
        this.totalAcertadas = totalAcertadas;

        dificultad = DIFICULTAD.Any;
        descripcion="";
        activado=true;
        numeroPreguntas=10;
    }

    public ModoJuego(String titulo, String descripcion, boolean activado, int numeroPreguntas, CATEGORIA categoria, DIFICULTAD dificultad) {
        this.titulo = titulo;
        this.categoria = categoria;
        this.maximaPuntuacion = "";
        this.totalAcertadas = "";

        this.dificultad = dificultad;
        this.descripcion=descripcion;
        this.activado=activado;
        this.numeroPreguntas=numeroPreguntas;
    }

    public int getNumeroPreguntas() {
        return numeroPreguntas;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getMaximaPuntuacion() {
        return maximaPuntuacion;
    }

    public void setMaximaPuntuacion(String maximaPuntuacion) {
        this.maximaPuntuacion = maximaPuntuacion;
    }

    public String getTotalAcertadas() {
        return totalAcertadas;
    }

    public void setTotalAcertadas(String totalAcertadas) {
        this.totalAcertadas = totalAcertadas;
    }

    public CATEGORIA getCategoria() {
        return categoria;
    }

    public void setCategoria(CATEGORIA categoria) {
        this.categoria = categoria;
    }

    public DIFICULTAD getDificultad() {
        return dificultad;
    }

    public void setDificultad(DIFICULTAD dificultad) {
        this.dificultad = dificultad;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public boolean isActivado() {
        return activado;
    }

    public void setActivado(boolean activado) {
        this.activado = activado;
    }
}
