package com.garcia.adrian.triviaapp.model.historial;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.garcia.adrian.triviaapp.data.AppDatabase;

import java.util.List;

public class HistorialPreguntasViewModel extends AndroidViewModel {

    private LiveData<List<Pregunta>> listaPreguntas;
    private static AppDatabase db;

    public HistorialPreguntasViewModel(@NonNull Application application, int idPregunta) {
        super(application);
        db = AppDatabase.getInstance (application);
        listaPreguntas = db.preguntaDAO().getPreguntas(idPregunta);
    }

    public LiveData<List<Pregunta>> getPreguntas() {
        return listaPreguntas;
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