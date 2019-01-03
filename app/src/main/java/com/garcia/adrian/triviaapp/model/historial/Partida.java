package com.garcia.adrian.triviaapp.model.historial;


import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;
import android.support.annotation.NonNull;

import com.garcia.adrian.triviaapp.R;
import com.garcia.adrian.triviaapp.enums.CATEGORIA;
import com.garcia.adrian.triviaapp.fragments.CategoryFragment;
import com.garcia.adrian.triviaapp.model.menu.EstiloJuegoBase;
import com.garcia.adrian.triviaapp.util.CategoriaConverter;
import com.garcia.adrian.triviaapp.util.DateConverter;

import java.sql.Date;

@Entity(tableName = "Partida")
public class Partida extends EstiloJuegoBase {

    @PrimaryKey(autoGenerate = true)
    private long id;             // ID Partida
    @NonNull
    private int puntuacion;
    @NonNull
    private int acertadas;
    @NonNull
    @TypeConverters({CategoriaConverter.class})
    private CATEGORIA categoria;
    @NonNull
    @TypeConverters({DateConverter.class})
    private Date fecha;

    public Partida () {}

    // Constructor para el modo historial
    public Partida(long id, int puntuacion, int acertadas, CATEGORIA categoria, Date fecha) {
        this.id = id;
        this.puntuacion = puntuacion;
        this.acertadas = acertadas;
        this.categoria = categoria;
        this.fecha = fecha;
    }

    @Override
    public String getTitulo () {
        return id+"";
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    @NonNull
    public int getPuntuacion() {
        return puntuacion;
    }

    public void setPuntuacion(@NonNull int puntuacion) {
        this.puntuacion = puntuacion;
    }

    @NonNull
    public int getAcertadas() {
        return acertadas;
    }

    public void setAcertadas(@NonNull int acertadas) {
        this.acertadas = acertadas;
    }

    @NonNull
    public CATEGORIA getCategoria() {
        return categoria;
    }

    public void setCategoria(@NonNull CATEGORIA categoria) {
        this.categoria = categoria;
    }

    @NonNull
    public Date getFecha() {
        return fecha;
    }

    public void setFecha(@NonNull Date fecha) {
        this.fecha = fecha;
    }
}
