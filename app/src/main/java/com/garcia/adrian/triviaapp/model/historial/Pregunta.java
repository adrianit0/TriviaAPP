package com.garcia.adrian.triviaapp.model.historial;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;


@Entity(tableName = "Pregunta",
        foreignKeys =  @ForeignKey(
                    entity = Partida.class,
                    parentColumns = "id",
                    childColumns = "partida_id"))

public class Pregunta {

    @PrimaryKey(autoGenerate = true)
    private long id;             // ID Pregunta
    @NonNull
    private String nombrePregunta;
    @NonNull
    private String tuRespuesta;
    @NonNull
    private String respuestaCorrecta;
    @NonNull
    private int puntos;

    @ColumnInfo(name = "partida_id")
    public long partidaId;


    public Pregunta(long id, @NonNull String nombrePregunta, @NonNull String tuRespuesta, @NonNull String respuestaCorrecta, @NonNull int puntos, @NonNull long partidaId) {
        this.id = id;
        this.nombrePregunta = nombrePregunta;
        this.tuRespuesta = tuRespuesta;
        this.respuestaCorrecta = respuestaCorrecta;
        this.puntos = puntos;
        this.partidaId = partidaId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getPartidaId() {
        return partidaId;
    }

    public void setPartidaId(long partidaId) {
        this.partidaId = partidaId;
    }

    @NonNull
    public String getNombrePregunta() {
        return nombrePregunta;
    }

    public void setNombrePregunta(@NonNull String nombrePregunta) {
        this.nombrePregunta = nombrePregunta;
    }

    @NonNull
    public String getTuRespuesta() {
        return tuRespuesta;
    }

    public void setTuRespuesta(@NonNull String tuRespuesta) {
        this.tuRespuesta = tuRespuesta;
    }

    @NonNull
    public String getRespuestaCorrecta() {
        return respuestaCorrecta;
    }

    public void setRespuestaCorrecta(@NonNull String respuestaCorrecta) {
        this.respuestaCorrecta = respuestaCorrecta;
    }

    @NonNull
    public int getPuntos() {
        return puntos;
    }

    public void setPuntos(@NonNull int puntos) {
        this.puntos = puntos;
    }
}
