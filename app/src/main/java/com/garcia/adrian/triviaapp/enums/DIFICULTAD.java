package com.garcia.adrian.triviaapp.enums;

// Un enum para la dificultad
public enum DIFICULTAD {
    Any ("", 0), Easy ("easy", 100), Medium ("medium", 200), Hard("hard", 300);

    private String texto;
    private int puntuacion;

    DIFICULTAD (String texto, int puntuacion){
        this.texto = texto;
        this.puntuacion = puntuacion;
    }

    public String getTexto() {
        return texto;
    }

    public int getPuntuacion() {
        return puntuacion;
    }
};