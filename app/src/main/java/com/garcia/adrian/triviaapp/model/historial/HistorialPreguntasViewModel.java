package com.garcia.adrian.triviaapp.model.historial;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.garcia.adrian.triviaapp.data.AppDatabase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HistorialPreguntasViewModel extends AndroidViewModel {

    private static AppDatabase db;

    private int idTabla=-1;
    private Pregunta[] listaPreguntas;

    public HistorialPreguntasViewModel(@NonNull Application application) {
        super(application);
        db = AppDatabase.getInstance (application);
        //listaPreguntas = db.preguntaDAO().getPreguntas();
    }

    // Busca las preguntas haciendo uso de un callback que se activa al ser buscado la info
    public void getPreguntas (int id, OnGetQuery callback) {
        if (idTabla>=0 && idTabla==id && listaPreguntas!=null) {
            callback.onGetPreguntas(listaPreguntas);
            return;
        }

        listaPreguntas=null;
        idTabla=id;

        new HistorialPreguntasViewModel.SelectAsyncPreguntaDB(callback).execute(id);
    }

    private class SelectAsyncPreguntaDB extends AsyncTask<Integer, Void, Pregunta[]> {

        private OnGetQuery callback;

        public SelectAsyncPreguntaDB(OnGetQuery callback) {
            this.callback = callback;
        }

        @Override
        protected Pregunta[] doInBackground(Integer... id) {
            List<Pregunta> preguntas;

            if (id.length!=0) {
                preguntas = db.preguntaDAO().getPreguntasList(id[0]);
            } else {
                return null;
            }

            Pregunta[] array = new Pregunta[preguntas.size()];
            for (int i = 0; i < array.length; i++) {
                array[i] = preguntas.get(i);
            }

            return array;
        }

        @Override
        protected void onPostExecute(Pregunta[] preguntas) {
            if (preguntas==null)
                return;

            listaPreguntas = preguntas;

            if (callback!=null)
                callback.onGetPreguntas(preguntas);
        }
    }

    public void addPregunta (Pregunta... preguntas) {
        new HistorialPreguntasViewModel.AddAsyncPreguntaDB().execute(preguntas);
    }

    public void actualizarPregunta (Pregunta pregunta) {
        new HistorialPreguntasViewModel.EditAsyncPreguntaDB().execute(pregunta);
    }

    public void borrarPregunta (Pregunta pregunta) {
        new HistorialPreguntasViewModel.DeleteAsyncPreguntaDB().execute(pregunta);
    }

    // Para crear un callback en el momento en el que se hayase metido en la bbdd la partida
    // Y así poder incluir las preguntas con la ID que se le ha dado
    public interface OnGetQuery {
        public void onGetPreguntas(Pregunta[] preguntas);
    }



    private class AddAsyncPreguntaDB extends AsyncTask<Pregunta, Void, Long> {

        @Override
        protected Long doInBackground(Pregunta... preguntas) {
            long id = -1;

            if (preguntas.length!=0) {
                for (int i = 0; i < preguntas.length; i++) {
                    id=db.preguntaDAO().insertPregunta(preguntas[i]);
                    preguntas[i].setId(id);
                }
            }

            return id;
        }

        @Override
        protected void onPostExecute(Long id) {
            if (id == -1) {
                Toast.makeText(getApplication(), "Error añadiendo la pregunta!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getApplication(), "Pregunta Añadida!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private class EditAsyncPreguntaDB extends AsyncTask<Pregunta, Void, Integer> {

        @Override
        protected Integer doInBackground(Pregunta... preguntas) {
            int preguntasCambiadas=0;

            if (preguntas.length!=0) {
                preguntasCambiadas = db.preguntaDAO().updatePregunta(preguntas[0]);
            }

            return preguntasCambiadas;
        }

        @Override
        protected void onPostExecute(Integer id) {
            if (id <=0 ) {
                Toast.makeText(getApplication(), "Error actualizando la pregunta!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getApplication(), "Pregunta actualizada!", Toast.LENGTH_SHORT).show();

            }
        }
    }

    private class DeleteAsyncPreguntaDB extends AsyncTask<Pregunta, Void, Integer> {

        @Override
        protected Integer doInBackground(Pregunta... preguntas) {
            int preguntasCambiadas=0;

            if (preguntas.length!=0) {
                preguntasCambiadas = db.preguntaDAO().deletePregunta(preguntas[0]);
            }

            return preguntasCambiadas;
        }

        @Override
        protected void onPostExecute(Integer id) {
            if (id == -1) {
                Toast.makeText(getApplication(), "Error borrando la pregunta!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getApplication(), "Pregunta borrada!", Toast.LENGTH_SHORT).show();
            }
        }
    }
}