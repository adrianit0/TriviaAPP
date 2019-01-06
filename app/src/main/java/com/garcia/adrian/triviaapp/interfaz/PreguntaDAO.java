package com.garcia.adrian.triviaapp.interfaz;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.garcia.adrian.triviaapp.model.historial.Pregunta;

import java.util.List;

@Dao
public interface PreguntaDAO {

    @Insert (onConflict = OnConflictStrategy.REPLACE)
    public long insertPregunta (Pregunta pregunta);

    @Insert
    public void insertPregunta (Pregunta... preguntas);

    @Update
    public int updatePregunta (Pregunta pregunta);

    @Delete
    public int deletePregunta (Pregunta pregunta);

    @Query("SELECT * FROM Pregunta WHERE id=:id")
    public Pregunta getPregunta (long id);

    @Query("SELECT * FROM Pregunta")
    public LiveData<List<Pregunta>> getPreguntas();

    @Query("SELECT * FROM Pregunta WHERE partida_id=:idPregunta")
    public List<Pregunta> getPreguntasList(int idPregunta);
}
