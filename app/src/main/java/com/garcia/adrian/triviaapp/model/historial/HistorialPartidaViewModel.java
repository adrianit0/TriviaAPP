package com.garcia.adrian.triviaapp.model.historial;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.garcia.adrian.triviaapp.data.AppDatabase;
import com.garcia.adrian.triviaapp.model.historial.Partida;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HistorialPartidaViewModel extends AndroidViewModel {

    private LiveData<List<Partida>> listaPartidas;
    private static AppDatabase db;

    public HistorialPartidaViewModel(@NonNull Application application) {
        super(application);
        db = AppDatabase.getInstance (application);
        listaPartidas = db.partidaDao().getPartidas();
    }

    public LiveData<List<Partida>> getPartidas() {
        return listaPartidas;
    }

    public void addPartida (Partida partida, OnAddRowListener listener) {
        new AddAsyncPartidaDB(listener).execute(partida);
    }

    public void actualizarPartida (Partida partida) {
        new EditAsyncPartidaDB().execute(partida);
    }

    public void borrarPartida (Partida partida) {
        new DeleteAsyncPartidaDB().execute(partida);
    }

    // Para crear un callback en el momento en el que se hayase metido en la bbdd la partida
    // Y así poder incluir las preguntas con la ID que se le ha dado
    public interface OnAddRowListener {
        public void onRowAdded(long id);
    }

    private class AddAsyncPartidaDB extends AsyncTask<Partida, Void, Long> {

        private OnAddRowListener callback;

        public AddAsyncPartidaDB (OnAddRowListener listener) {
            callback = listener;
        }

        @Override
        protected Long doInBackground(Partida... partidas) {
            long id = -1;

            if (partidas.length!=0) {
                Partida partida = partidas[0];
                id = db.partidaDao().insertPartida(partida);
                partida.setId(id);
            }

            return id;
        }

        @Override
        protected void onPostExecute(Long id) {
            if (id == -1) {
                Toast.makeText(getApplication(), "Error añadiendo la partida!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getApplication(), "partida Añadida!", Toast.LENGTH_SHORT).show();
                // Ejecutamos el callback
                if(callback!=null)
                    callback.onRowAdded(id);
            }
        }
    }

    private class EditAsyncPartidaDB extends AsyncTask<Partida, Void, Integer> {

        @Override
        protected Integer doInBackground(Partida... partidas) {
            int partidasCambiadas=0;

            if (partidas.length!=0) {
                partidasCambiadas = db.partidaDao().updatePartida(partidas[0]);
            }

            return partidasCambiadas;
        }

        @Override
        protected void onPostExecute(Integer id) {
            if (id == -1) {
                Toast.makeText(getApplication(), "Error actualizando la partida!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getApplication(), "partida actualizada!", Toast.LENGTH_SHORT).show();

            }
        }
    }

    private class DeleteAsyncPartidaDB extends AsyncTask<Partida, Void, Integer> {

        @Override
        protected Integer doInBackground(Partida... partidas) {
            int partidasCambiadas=0;

            if (partidas.length!=0) {
                partidasCambiadas = db.partidaDao().deletePartida(partidas[0]);
            }

            return partidasCambiadas;
        }

        @Override
        protected void onPostExecute(Integer id) {
            if (id == -1) {
                Toast.makeText(getApplication(), "Error borrando la partida!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getApplication(), "partida borrada!", Toast.LENGTH_SHORT).show();
            }
        }
    }
}