package com.garcia.adrian.triviaapp.model.menu;

import com.garcia.adrian.triviaapp.model.menu.EstiloJuegoBase;

public class ModoJuego extends EstiloJuegoBase {

    private String descripcion;         // Solo para en el de modo juego
    private boolean bloqueado;          // Solo para modo juego


    // Constructor para
    public ModoJuego(String titulo, String descripcion) {
        super(titulo);
        this.descripcion = descripcion;
        this.bloqueado = false;
    }

    // Constructor para
    public ModoJuego(String titulo, String descripcion, boolean bloqueado) {
        super(titulo);
        this.descripcion = descripcion;
        this.bloqueado = bloqueado;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public boolean isBloqueado() {
        return bloqueado;
    }

    public void setBloqueado(boolean bloqueado) {
        this.bloqueado = bloqueado;
    }
}
