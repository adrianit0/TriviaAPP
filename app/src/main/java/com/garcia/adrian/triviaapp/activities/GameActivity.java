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
import android.widget.Toast;

import com.garcia.adrian.triviaapp.R;
import com.garcia.adrian.triviaapp.fragments.FragmentJuegoPregunta;
import com.garcia.adrian.triviaapp.fragments.FragmentJuegoRespuestas;
import com.garcia.adrian.triviaapp.model.juego.PreguntaJuego;
import com.garcia.adrian.triviaapp.model.juego.PreguntaJuegoViewModel;

import java.util.ArrayList;
import java.util.List;

public class GameActivity extends AppCompatActivity implements FragmentJuegoPregunta.OnQuestionSend, FragmentJuegoRespuestas.OnQuestionSend {

    private ArrayList<PreguntaJuego> listaPreguntas;
    private int preguntaActual = 0;

    private boolean cargado = false;

    private FragmentJuegoPregunta fragmentPregunta;
    private FragmentJuegoRespuestas fragmentRespuestas;
    private PreguntaJuegoViewModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        fragmentPregunta= (FragmentJuegoPregunta) getSupportFragmentManager().findFragmentById(R.id.fragmentPregunta);
        fragmentRespuestas= (FragmentJuegoRespuestas) getSupportFragmentManager().findFragmentById(R.id.fragmentRespuestas);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void onQuestionChange() {
        // Para que se muestre la pregunta tiene que haber cargado antes el otro fragment
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
    public void onAnswerChange() {
        // Para que se muestre la pregunta tiene que haber cargado antes el otro fragment
        if (!cargado) {
            cargado=true;
            return;
        }
        actualizarContenido();
    }

    @Override
    public void nextAnswer() {
        siguientePregunta(false);
    }

    // Actualiza el contenido de las preguntas (Para el juego)
    private void actualizarContenido () {
        // Create a new {@link ArrayAdapter} of listaPreguntas
        listaPreguntas = new ArrayList<>();


        // Get a reference to the ConnectivityManager to check state of network connectivity
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        // Get details on the currently active default data network
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        // If there is a network connection, fetch data
        if (networkInfo != null && networkInfo.isConnected()) {

            model = ViewModelProviders.of(this).get(PreguntaJuegoViewModel.class);

            model.getPreguntas().observe(this, new Observer<List<PreguntaJuego>>() {
                @Override
                public void onChanged(List<PreguntaJuego> preguntas) {
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
        if (!firstTime)
            preguntaActual++;

        if (preguntaActual>=listaPreguntas.size()) {
            Toast.makeText(GameActivity.this, "NO HAY MÁS PREGUNTAS", Toast.LENGTH_SHORT).show();
            return;
        }

        if (fragmentPregunta!=null){
            fragmentPregunta.setPregunta(listaPreguntas.get(preguntaActual),model.getPuntuacion(),preguntaActual);
        } else {
            Toast.makeText(GameActivity.this, "FRAGMENT PREGUNTA NO ENCONTRADO", Toast.LENGTH_SHORT).show();
        }

        if (fragmentRespuestas!=null){
            fragmentRespuestas.setRespuestas(listaPreguntas.get(preguntaActual));
        } else {
            Toast.makeText(GameActivity.this, "FRAGMENT  RESPUESTAS NO ENCONTRADO", Toast.LENGTH_SHORT).show();
        }
    }
}