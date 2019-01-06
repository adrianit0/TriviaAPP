package com.garcia.adrian.triviaapp.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.garcia.adrian.triviaapp.R;
import com.garcia.adrian.triviaapp.activities.GameActivity;
import com.garcia.adrian.triviaapp.activities.MainActivity;
import com.garcia.adrian.triviaapp.model.juego.PreguntaJuego;

public class FragmentJuegoRespuestas extends Fragment {

    private OnAnswerSend callback;

    private LinearLayout layoutRespuesta;
    private Button[] botones;
    private LinearLayout layoutRespuestaGameOver;
    private Button botonAtras;


    private boolean respondido = false;
    private PreguntaJuego pregunta;

    public FragmentJuegoRespuestas() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_respuestas, container, false);

        layoutRespuesta = view.findViewById(R.id.fragmentLinearRespuestas);
        botones = new Button[4];
        botones[0] = (Button) view.findViewById(R.id.respuesta1);
        botones[1] = (Button) view.findViewById(R.id.respuesta2);
        botones[2] = (Button) view.findViewById(R.id.respuesta3);
        botones[3] = (Button) view.findViewById(R.id.respuesta4);

        layoutRespuestaGameOver = view.findViewById(R.id.fragmentLinearRespuestasGameOVer);
        botonAtras = (Button) view.findViewById(R.id.botonVolver);

        layoutRespuesta.setVisibility(View.GONE);
        layoutRespuestaGameOver.setVisibility(View.GONE);

        for (int i = 0; i < botones.length; i++) {
            final int x = i;
            botones[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Si ya has respondido una opción no te dejará pulsar otro botón
                    if (respondido)
                        return;

                    // Bloqueamos para evitar poder responder otra respuesta.
                    respondido=true;

                    pregunta.setYourAnswer(x);
                    boolean acertado = pregunta.isCorrect();

                    ((Button)v).setTextColor((acertado)?getResources().getColor(R.color.colorRespuestaCorrecta):getResources().getColor(R.color.colorRespuestaIncorrecta));

                    // Si no lo acierta te dirá cual es la respuesta correcta.
                    if (!acertado)
                        botones[pregunta.getCorrectAnswer()].setTextColor(getResources().getColor(R.color.colorRespuestaCorrecta));

                    // Siguiente pregunta
                    if (callback!=null)
                        callback.onStartAnimation(acertado);
                }
            });
        }

        botonAtras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finishActivity(0);
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
            }
        });

        if (callback!=null)
            callback.onAnswerLoaded();

        return view;
    }

    public void setRespuestas (PreguntaJuego pregunta) {
        respondido = false;
        this.pregunta=pregunta;

        String[] opciones = pregunta.getOpcionesRandomly();
        for (int i = 0; i < opciones.length; i++) {
            botones[i].setTextColor(getResources().getColor(R.color.colorPregunta));
            botones[i].setText(opciones[i]);
            botones[i].setVisibility(View.VISIBLE);
        }

        for (int i = opciones.length; i < 4; i++) {
            botones[i].setVisibility(View.GONE);
        }
    }

    // Muestra el LinearLayout de las preguntas
    public void iniciarPartida() {
        layoutRespuesta.setVisibility(View.VISIBLE);
    }

    // Muestra el LinearLayout de fin de partida
    public void finalizarPartida() {
        layoutRespuesta.setVisibility(View.GONE);
        layoutRespuestaGameOver.setVisibility(View.VISIBLE);
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            callback = (OnAnswerSend) context;
        }catch (ClassCastException e){
            System.out.println("Error: deberia implementar la interfaz");
        }
    }

    public interface OnAnswerSend {
        void onAnswerLoaded();
        void onStartAnimation(boolean acertada);
    }
}
