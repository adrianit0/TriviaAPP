package com.garcia.adrian.triviaapp.model.juego;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.garcia.adrian.triviaapp.enums.CATEGORIA;
import com.garcia.adrian.triviaapp.enums.DIFICULTAD;
import com.garcia.adrian.triviaapp.util.QueryUtils;

import java.util.List;

/*
* El ViewModel del contenido que ocurra dentro del activity de las partidas.
* */
public class PreguntaJuegoViewModel extends AndroidViewModel {

    private MutableLiveData<List<PreguntaJuego>> preguntasJuego;
    private int puntuacion=0;
    private int preguntaActual=0;
    private Application aplicacion;

    // traducción
    private Traduccion traduccion=null;
    private boolean isTranslate=false;      // Se ha traducido
    private boolean isTranslating=false;    // Se está traduciendo

    private boolean[] comodines;            // Comodines


    private static final String REQUEST_URL = "https://opentdb.com/api.php";
    private static final String TRANSLATE_REQUEST_URL = "https://translate.yandex.net/api/v1.5/tr.json/translate?key=<PONERKEYAQUI>";

    public PreguntaJuegoViewModel (@NonNull Application aplicacion) {
        super (aplicacion);
        this.aplicacion = aplicacion;
        comodines = new boolean[]{false, false, false};
    }

    // Método por defecto para descargar los datos de las preguntas
    public LiveData<List<PreguntaJuego>> getPreguntas(boolean forzar) {
        if (preguntasJuego==null || forzar) {
            preguntasJuego= new MutableLiveData<>();
            cargarPreguntas(10, CATEGORIA.AnyCategory, DIFICULTAD.Any);
        }

        return preguntasJuego;
    }

    // Sobrecarga por defecto, cogerá las preguntas sin forzar que se vuelvan a descargar los datos
    public LiveData<List<PreguntaJuego>> getPreguntas() {
        return getPreguntas(false);
    }

    //Carga una serie de preguntas a partir de unos requisitos
    public LiveData<List<PreguntaJuego>> getPreguntas(int numeroPreguntas, CATEGORIA categoria, DIFICULTAD dificultad, boolean forzar) {
        if (preguntasJuego==null || forzar) {
            preguntasJuego= new MutableLiveData<>();
            cargarPreguntas(numeroPreguntas, categoria, dificultad);
        }
        return preguntasJuego;
    }

    public int getPuntuacion () {
        return puntuacion;
    }

    public void ganarPuntos (int puntos) {
        puntuacion+=puntos;
    }

    public int getPreguntaActual() {
        return preguntaActual;
    }

    public void setPreguntaActual(int p) {
        preguntaActual=p;
    }

    private void cargarPreguntas (int cantidadPreguntas, CATEGORIA categoria, DIFICULTAD dificultad) {
        //Log.e("CARGANDO_PREGUNTAS", "Las preguntas estan siendo cargada. CATEGORIA: "+ categoria+ " ID: "+categoria.getID());

        String tipo = "multiple";       // Tipo: multiple, boolean, any

        Uri baseUri = Uri.parse(REQUEST_URL);
        Uri.Builder uriBuilder = baseUri.buildUpon();

        uriBuilder.appendQueryParameter("amount", cantidadPreguntas+"");
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


    // TRADUCCION
    public void cargarTraduccion (PreguntaJuego preguntaJuego, final OnTranslateListener callback) {
        if (preguntaJuego==null) {
            return;
        }

        if (isTranslating) {
            Toast.makeText(aplicacion.getBaseContext(), "Se está cargando la traduccion", Toast.LENGTH_SHORT).show();
            return;
        }

        if (traduccion!=null || isTranslate) {
            // Si ya está traducido no hará falta cargarlo
            cargarTraduccion(callback);
            return;
        }

        Uri baseUri = Uri.parse(TRANSLATE_REQUEST_URL);
        Uri.Builder uriBuilder = baseUri.buildUpon();

        uriBuilder.appendQueryParameter("text", preguntaJuego.getEnunciado());
        String[] opciones = preguntaJuego.getOpcionesRandomly(false);
        for (String o : opciones) {
            uriBuilder.appendQueryParameter("text", o);
        }
        uriBuilder.appendQueryParameter("lang", "en-es");


        final RequestQueue requestQueue = Volley.newRequestQueue(aplicacion.getApplicationContext());
        StringRequest request = new StringRequest(Request.Method.GET, uriBuilder.toString(),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        traduccion = QueryUtils.translateQuestion(response);
                        isTranslating = false;

                        cargarTraduccion(callback);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Mostramos el mensaje de error
                (Toast.makeText(aplicacion.getApplicationContext(), "Error: No tienes conexión a internet o se ha producido un error aleatorio" /*+error.getMessage()*/, Toast.LENGTH_SHORT)).show();
                isTranslating = false;
            }
        });
        requestQueue.add(request);

        isTranslating= true;
    }

    private void cargarTraduccion (OnTranslateListener callback) {
        if(traduccion==null || isTranslating)
            return;

        isTranslate = !isTranslate;

        callback.onTranslate(traduccion, isTranslate);
    }

    public boolean getComodin (int pos) {
        return comodines[pos];
    }

    public void usarComodin (int pos){
        comodines[pos]=true;
    }

    public void resetTranslate() {
        traduccion=null;
        isTranslate=false;
    }

    public interface OnTranslateListener {
        void onTranslate(Traduccion traduccion, boolean traducido);
    }
}
