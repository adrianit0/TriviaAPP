package com.garcia.adrian.triviaapp.model.juego;

public class Traduccion {

    private String enunciado;
    private String[] opciones;

    public Traduccion(String[] opciones) {
        this.enunciado = opciones[0];
        this.opciones = new String[opciones.length-1];
        for (int i= 0; i < this.opciones.length; i++) {
            this.opciones[i] = opciones[i+1];
        }
    }

    public String getEnunciado() {
        return enunciado;
    }

    public void setEnunciado(String enunciado) {
        this.enunciado = enunciado;
    }

    public String[] getOpciones() {
        return opciones;
    }

    public void setOpciones(String[] opciones) {
        this.opciones = opciones;
    }
}