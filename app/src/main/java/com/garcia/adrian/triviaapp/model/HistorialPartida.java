package com.garcia.adrian.triviaapp.model;


import android.content.Context;
import android.content.res.Resources;

import com.garcia.adrian.triviaapp.R;
import com.garcia.adrian.triviaapp.activities.MainActivity;

public class HistorialPartida extends EstiloJuegoBase{

    private int id;             // ID Partida
    private String puntuacion;
    private String acertadas;
    private String categoria;
    private String fecha;

    // Constructor para el modo historial
    public HistorialPartida(Context context, int id, String puntuacion, String acertadas, String categoria, String fecha) {
        super(context.getString(R.string.tituloHistorial)+" #"+id);
        this.id = id;
        this.puntuacion = puntuacion;
        this.acertadas = acertadas;
        this.categoria = categoria;
        this.fecha = fecha;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
