package com.garcia.adrian.triviaapp.model.historial;


import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.content.Context;
import android.support.annotation.NonNull;

import com.garcia.adrian.triviaapp.R;
import com.garcia.adrian.triviaapp.model.menu.EstiloJuegoBase;

@Entity(tableName = "partida")
public class Partida extends EstiloJuegoBase {

    @PrimaryKey(autoGenerate = true)
    private long id;             // ID Partida
    @NonNull
    private String puntuacion;
    @NonNull
    private String acertadas;
    @NonNull
    private String categoria;
    @NonNull
    private String fecha;

    public Partida () {}

    // Constructor para el modo historial
    public Partida(Context context, long id, String puntuacion, String acertadas, String categoria, String fecha) {
        super(context.getString(R.string.tituloHistorial)+" #"+id);
        this.id = id;
        this.puntuacion = puntuacion;
        this.acertadas = acertadas;
        this.categoria = categoria;
        this.fecha = fecha;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
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
