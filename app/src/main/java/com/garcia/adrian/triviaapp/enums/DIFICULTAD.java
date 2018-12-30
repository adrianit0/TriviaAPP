package com.garcia.adrian.triviaapp.enums;

import android.content.Context;

import com.garcia.adrian.triviaapp.R;

// Un enum para la dificultad
public enum DIFICULTAD {
    Any ("", 0), Easy ("easy", 100), Medium ("medium", 200), Hard("hard", 300);

    private String texto;
    private int puntuacion;

    DIFICULTAD (String texto, int puntuacion){
        this.texto = texto;
        this.puntuacion = puntuacion;
    }

    public String getName (Context res) {
        switch (texto) {
            case "easy":
                return res.getString(R.string.dificultadFacil);
            case "medium":
                return res.getString(R.string.dificultadMedium);
            case "hard":
                return res.getString(R.string.dificultadHard);
        }
        return res.getString(R.string.dificultadAny);
    }

    public String getTexto() {
        return texto;
    }

    public int getPuntuacion() {
        return puntuacion;
    }

    public static DIFICULTAD fromString (String cadena) {
        switch (cadena) {
            case "easy":
                return DIFICULTAD.Easy;
            case "medium":
                return DIFICULTAD.Medium;
            case "hard":
                return DIFICULTAD.Hard;
        }

        return DIFICULTAD.Any;
    }
};