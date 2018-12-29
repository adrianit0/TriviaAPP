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
import com.garcia.adrian.triviaapp.model.juego.PreguntaJuego;
import com.garcia.adrian.triviaapp.model.juego.PreguntaJuegoViewModel;

import java.util.ArrayList;
import java.util.List;

public class GameActivity extends AppCompatActivity implements FragmentJuegoPregunta.OnQuestionSend {

    private ArrayList<PreguntaJuego> listaPreguntas;
    private int preguntaActual = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
    }

    @Override
    public void onChange () {
        FragmentJuegoPregunta f= (FragmentJuegoPregunta) getSupportFragmentManager().findFragmentById(R.id.frameLayoutContenido);

        actualizarContenido();

        if (preguntaActual>listaPreguntas.size()) {
            Toast.makeText(GameActivity.this, "NO HAY MÁS PREGUNTAS", Toast.LENGTH_SHORT).show();
            return;
        }

        if (f!=null){
            //Toast.makeText(MainActivity.this, "ENCONTRADO", Toast.LENGTH_SHORT).show();
            f.setPregunta(listaPreguntas.get(preguntaActual));
        } else {
            Toast.makeText(GameActivity.this, "FRAGMENT NO ENCONTRADO", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        actualizarContenido();
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

            PreguntaJuegoViewModel model = ViewModelProviders.of(this).get(PreguntaJuegoViewModel.class);

            model.getPreguntas().observe(this, new Observer<List<PreguntaJuego>>() {
                @Override
                public void onChanged(List<PreguntaJuego> preguntas) {
                    listaPreguntas = new ArrayList<>();
                    listaPreguntas.addAll(preguntas);

                    for (PreguntaJuego p : listaPreguntas) {
                        Log.e("PRUEBA_PREGUNTA", p.getEnunciado());
                    }
                }
            });

        } else {
            (Toast.makeText(this, R.string.no_internet_connection, Toast.LENGTH_LONG)).show();
        }
    }
}
