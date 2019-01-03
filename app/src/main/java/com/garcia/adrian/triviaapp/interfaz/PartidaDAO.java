package com.garcia.adrian.triviaapp.interfaz;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.garcia.adrian.triviaapp.model.historial.Partida;
import com.garcia.adrian.triviaapp.model.historial.Pregunta;

import java.util.List;

@Dao
public interface PartidaDAO {
    @Insert
    public long insertPartida (Partida partida);

    @Insert
    public void insertPartidas (Partida... partidas);

    @Insert
    public void insertPartidasConPreguntas (Partida partida, List<Pregunta> preguntas);

    @Update
    public int updatePartida (Partida partida);

    @Delete
    public int deletePartida (Partida partida);

    @Query("SELECT * FROM Partida WHERE id=:id")
    public Partida getPartida (long id);

    @Query("SELECT * FROM Partida")
    public LiveData<List<Partida>> getPartidas();
}

/*
//Contenido de mi QUERY a usar, transformado en ROOM

// nombre BBDD
partidas_jugadas.db

CREATE TABLE Categoria (
	id integer,
  	nombre TEXT
);

INSERT INTO Categoria VALUES (-1, "Any");

CREATE TABLE Partida (
	id INTEGER PRIMARY KEY AUTOINCREMENT,
  	categoria INTEGER,
  	fecha DATE,
  	categoria INTEGER
  	FOREIGN KEY(categoria) REFERENCES Categoria(id)
);

CREATE TABLE Pregunta (
  id INTEGER PRIMARY KEY AUTOINCREMENT,
  enunciado TEXT,
  respuesta TEXT,
  tuRespuesta TEXT,
  puntos INTEGER,
  idPartida INTEGER,
  FOREIGN KEY(idPartida) REFERENCES Partida(id)
);

*/