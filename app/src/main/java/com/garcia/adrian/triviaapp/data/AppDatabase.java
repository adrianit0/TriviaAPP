package com.garcia.adrian.triviaapp.data;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.garcia.adrian.triviaapp.interfaz.PartidaDAO;
import com.garcia.adrian.triviaapp.interfaz.PreguntaDAO;
import com.garcia.adrian.triviaapp.model.historial.Partida;
import com.garcia.adrian.triviaapp.model.historial.Pregunta;

@Database(entities = {Partida.class, Pregunta.class}, version = 3, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract PartidaDAO partidaDao();
    public abstract PreguntaDAO preguntaDAO();

    private static AppDatabase INSTANCE=null;

    public static AppDatabase getInstance (final Context context) {
        if (INSTANCE==null) {
            INSTANCE= Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "partidas_jugadas.db").fallbackToDestructiveMigration().build();
        }

        return INSTANCE;
    }

    public void destroyInstance() { INSTANCE=null; }
}