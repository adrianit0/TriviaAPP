package com.garcia.adrian.triviaapp.model.juego;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.garcia.adrian.triviaapp.R;
import com.garcia.adrian.triviaapp.util.QueryUtils;

import java.util.List;

/*
* El ViewModel del contenido que ocurra dentro del activity de las partidas.
* */
public class PreguntaJuegoViewModel extends AndroidViewModel {

    private static MutableLiveData<List<PreguntaJuego>> preguntasJuego;
    private Application aplicacion;
    private static final String REQUEST_URL = "https://opentdb.com/api.php";

    public PreguntaJuegoViewModel (@NonNull Application aplicacion) {
        super (aplicacion);
        this.aplicacion = aplicacion;
    }

    public LiveData<List<PreguntaJuego>> getPreguntas() {

        boolean forzar = false; // TODO: Cambiar esto
        if (preguntasJuego==null || forzar) {

            preguntasJuego= new MutableLiveData<>();
            cargarPreguntas();
        }

        return preguntasJuego;
    }

    private void cargarPreguntas () {
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(aplicacion.getApplicationContext());
        // Tomamos el contexto
        Context contexto = aplicacion.getApplicationContext();

        int numero = 10;                // Cantidad de preguntas
        int categoria = 23;             // Categoria
        String dificultad = "medium";   // Dificultad
        String tipo = "multiple";       // Tipo (Verdadero/Falso o Multiples opciones)

        Uri baseUri = Uri.parse(REQUEST_URL);
        Uri.Builder uriBuilder = baseUri.buildUpon();

        uriBuilder.appendQueryParameter("amount", numero+"");
        uriBuilder.appendQueryParameter("category", categoria+"");
        uriBuilder.appendQueryParameter("difficulty", dificultad);
        uriBuilder.appendQueryParameter("type", tipo);

        final RequestQueue requestQueue = Volley.newRequestQueue(aplicacion.getApplicationContext());
        StringRequest request = new StringRequest(Request.Method.GET, uriBuilder.toString(),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        List<PreguntaJuego> listaPreguntas = QueryUtils.extractQuestions(response);
                        preguntasJuego.setValue(listaPreguntas);

                        // Log del contenido
                        Log.e("Respuesta_funcional", response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Mostramos el mensaje de error
                (Toast.makeText(aplicacion.getApplicationContext(), "Error: " +error.getMessage(), Toast.LENGTH_SHORT)).show();
            }
        });
        requestQueue.add(request);

    }

}
