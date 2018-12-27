package com.garcia.adrian.triviaapp.data;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.garcia.adrian.triviaapp.interfaz.PartidaDAO;
import com.garcia.adrian.triviaapp.model.historial.Partida;

@Database(entities = {Partida.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract PartidaDAO partidaDao();
    private static AppDatabase INSTANCE=null;

    public static AppDatabase getInstance (final Context context) {
        if (INSTANCE==null) {
            INSTANCE= Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "partidas_jugadas.db").fallbackToDestructiveMigration().build();
        }

        return INSTANCE;
    }

    public void destroyInstance() { INSTANCE=null; }
}