package com.garcia.adrian.triviaapp.activities;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.garcia.adrian.triviaapp.R;
import com.garcia.adrian.triviaapp.fragments.FragmentJuegoPregunta;
import com.garcia.adrian.triviaapp.fragments.FragmentJuegoRespuestas;
import com.garcia.adrian.triviaapp.model.juego.PreguntaJuego;
import com.garcia.adrian.triviaapp.model.juego.PreguntaJuegoViewModel;
import com.garcia.adrian.triviaapp.model.menu.ModoJuego;

import java.util.ArrayList;
import java.util.List;

public class GameActivity extends AppCompatActivity implements FragmentJuegoPregunta.OnQuestionSend, FragmentJuegoRespuestas.OnAnswerSend {

    private ArrayList<PreguntaJuego> listaPreguntas;
    private int preguntaActual = 0;


    private FragmentJuegoPregunta fragmentPregunta;
    private boolean cargado = false;
    private FragmentJuegoRespuestas fragmentRespuestas;
    private PreguntaJuegoViewModel model;

    // Para crear la partida
    private ModoJuego modoSeleccionado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        fragmentPregunta= (FragmentJuegoPregunta) getSupportFragmentManager().findFragmentById(R.id.fragmentPregunta);
        fragmentRespuestas= (FragmentJuegoRespuestas) getSupportFragmentManager().findFragmentById(R.id.fragmentRespuestas);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void onQuestionLoaded() {
        // Para que se muestre la pregunta tiene que haber cargado antes el fragment de las respuestas
        if (!cargado) {
            cargado=true;
            return;
        }
        actualizarContenido();
    }

    @Override
    public void onAnswerLoaded() {
        // Para que se muestre la pregunta tiene que haber cargado antes el fragment de las preguntas
        if (!cargado) {
            cargado=true;
            return;
        }
        actualizarContenido();
    }

    @Override
    public void onStartAnimation (boolean acertado){
        if (acertado&&model!=null)
            model.ganarPuntos(listaPreguntas.get(preguntaActual).getDificultad().getPuntuacion());
        fragmentPregunta.animacion(acertado);
    }

    @Override
    public void nextAnswer() {
        siguientePregunta(false);
    }

    // Actualiza el contenido de las preguntas (Para el juego)
    private void actualizarContenido () {
        // Create a new {@link ArrayAdapter} of listaPreguntas
        listaPreguntas = new ArrayList<>();

        // Cogemos el modo de juego si no lo tenemos ya
        if (modoSeleccionado ==null) {
            Intent intent = getIntent();
            if (intent!=null) {
                ModoJuego c = (ModoJuego) intent.getSerializableExtra("modoJuego");

                if (c != null) {
                    modoSeleccionado = c;
                }
            } else {
                Log.e("CATEGORIA_NO_CREADA",  "No existe INTENT");
            }
        }

        // Get a reference to the ConnectivityManager to check state of network connectivity
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        // Get details on the currently active default data network
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        // If there is a network connection, fetch data
        if (networkInfo != null && networkInfo.isConnected()) {

            model = ViewModelProviders.of(this).get(PreguntaJuegoViewModel.class);

            preguntaActual = model.getPreguntaActual();

            model.getPreguntas(modoSeleccionado.getNumeroPreguntas(), modoSeleccionado.getCategoria(), modoSeleccionado.getDificultad(), false).observe(this, new Observer<List<PreguntaJuego>>() {
                @Override
                public void onChanged(List<PreguntaJuego> preguntas) {
                    // Ocultamos la barra de progreso
                    ProgressBar progressBar = findViewById(R.id.loading_indicator);
                    progressBar.setVisibility(View.GONE);

                    listaPreguntas = new ArrayList<>();
                    listaPreguntas.addAll(preguntas);

                    siguientePregunta(true);
                }
            });

        } else {
            (Toast.makeText(this, R.string.no_internet_connection, Toast.LENGTH_LONG)).show();
        }
    }

    private void siguientePregunta(boolean firstTime) {
        // Si es la primera se mostraran los LinearLayout, en caso contrario suma 1 el contador
        if (firstTime) {
            fragmentPregunta.iniciarPartida();
            fragmentRespuestas.iniciarPartida();
        } else {
            model.setPreguntaActual(++preguntaActual);
        }


        if (preguntaActual>=listaPreguntas.size()) {
            Toast.makeText(GameActivity.this, "NO HAY MÁS PREGUNTAS", Toast.LENGTH_SHORT).show();
            //TODO: Añadir
            fragmentPregunta.finalizarPartida(getPreguntasCorrectas(), listaPreguntas.size(), model.getPuntuacion());
            fragmentRespuestas.finalizarPartida();
            return;
        }

        if (fragmentPregunta!=null){
            fragmentPregunta.setPregunta(listaPreguntas.get(preguntaActual),model.getPuntuacion(),preguntaActual, listaPreguntas.size());
        } else {
            Toast.makeText(GameActivity.this, "FRAGMENT PREGUNTA NO ENCONTRADO", Toast.LENGTH_SHORT).show();
        }

        if (fragmentRespuestas!=null){
            fragmentRespuestas.setRespuestas(listaPreguntas.get(preguntaActual));
        } else {
            Toast.makeText(GameActivity.this, "FRAGMENT  RESPUESTAS NO ENCONTRADO", Toast.LENGTH_SHORT).show();
        }
    }

    // Devuelve el número de preguntas correctas
    private int getPreguntasCorrectas () {
        int count = 0;
        for(PreguntaJuego pj : listaPreguntas) {
            if (pj.isCorrect())
                count++;
        }
        return count;
    }
}