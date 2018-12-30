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
import com.garcia.adrian.triviaapp.enums.CATEGORIA;
import com.garcia.adrian.triviaapp.enums.DIFICULTAD;
import com.garcia.adrian.triviaapp.model.menu.ModoCategoria;
import com.garcia.adrian.triviaapp.util.QueryUtils;

import java.util.List;

/*
* El ViewModel del contenido que ocurra dentro del activity de las partidas.
* */
public class PreguntaJuegoViewModel extends AndroidViewModel {

    private MutableLiveData<List<PreguntaJuego>> preguntasJuego;
    private int puntuacion=0;
    private Application aplicacion;
    private static final String REQUEST_URL = "https://opentdb.com/api.php";

    public PreguntaJuegoViewModel (@NonNull Application aplicacion) {
        super (aplicacion);
        this.aplicacion = aplicacion;
    }

    public LiveData<List<PreguntaJuego>> getPreguntas(boolean forzar) {
        if (preguntasJuego==null || forzar) {
            preguntasJuego= new MutableLiveData<>();
            cargarPreguntas(10, CATEGORIA.AnyCategory, DIFICULTAD.Any);
        }

        return preguntasJuego;
    }

    public LiveData<List<PreguntaJuego>> getPreguntas() {
        return getPreguntas(false);
    }

    public int getPuntuacion () {
        return puntuacion;
    }

    public void ganarPuntos (int puntos) {
        puntuacion+=puntos;
    }

    //Carga unas partidas del modo categoria
    public LiveData<List<PreguntaJuego>> getPreguntas(ModoCategoria categoria) {
        preguntasJuego= new MutableLiveData<>();
        cargarPreguntas(10, categoria.getCategoria(), DIFICULTAD.Any);
        return preguntasJuego;
    }

    private void cargarPreguntas (int numero, CATEGORIA categoria, DIFICULTAD dificultad) {
        Log.e("CARGANDO_PREGUNTAS", "Las preguntas estan siendo cargada. CATEGORIA: "+ categoria+ " ID: "+categoria.getID());

        String tipo = "multiple";       // Tipo: multiple, boolean, any

        Uri baseUri = Uri.parse(REQUEST_URL);
        Uri.Builder uriBuilder = baseUri.buildUpon();

        uriBuilder.appendQueryParameter("amount", numero+"");
        if (categoria.getID()>0)
            uriBuilder.appendQueryParameter("category", categoria.getID()+"");
        if (!dificultad.equals("any"))
            uriBuilder.appendQueryParameter("difficulty", dificultad.getTexto());
        uriBuilder.appendQueryParameter("type", tipo);

        final RequestQueue requestQueue = Volley.newRequestQueue(aplicacion.getApplicationContext());
        StringRequest request = new StringRequest(Request.Method.GET, uriBuilder.toString(),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        preguntasJuego.setValue( QueryUtils.extractQuestions(response));
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
