package com.garcia.adrian.triviaapp.model.menu;

public abstract class EstiloJuegoBase {

    private String titulo;

    public EstiloJuegoBase() {
    }

    public EstiloJuegoBase(String titulo) {
        this.titulo = titulo;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
}
