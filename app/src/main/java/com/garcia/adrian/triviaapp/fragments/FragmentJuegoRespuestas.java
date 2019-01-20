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
import android.widget.TextView;
import android.widget.Toast;

import com.garcia.adrian.triviaapp.R;
import com.garcia.adrian.triviaapp.activities.GameActivity;
import com.garcia.adrian.triviaapp.activities.MainActivity;
import com.garcia.adrian.triviaapp.enums.CATEGORIA;
import com.garcia.adrian.triviaapp.enums.DIFICULTAD;
import com.garcia.adrian.triviaapp.model.juego.PreguntaJuego;
import com.garcia.adrian.triviaapp.model.juego.Traduccion;

import java.text.DecimalFormat;

public class FragmentJuegoRespuestas extends Fragment {

    private OnAnswerSend callback;

    private LinearLayout layoutRespuesta;
    private Button[] botones;
    private LinearLayout layoutRespuestaGameOver;
    private Button botonAtras;

    private Button[] comodines;
    private TextView textoComodin;


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
        botones[0] = view.findViewById(R.id.respuesta1);
        botones[1] = view.findViewById(R.id.respuesta2);
        botones[2] = view.findViewById(R.id.respuesta3);
        botones[3] = view.findViewById(R.id.respuesta4);

        comodines = new Button[3];
        comodines[0] = view.findViewById(R.id.comodin_llamar);
        comodines[1] = view.findViewById(R.id.comodin_mitad);
        comodines[2] = view.findViewById(R.id.comodin_publico);

        textoComodin = view.findViewById(R.id.comodin_respuesta);

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

        comodines[0].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                comodinLlamar();
            }
        });

        comodines[1].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                comodinMitad();
            }
        });

        comodines[2].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                comodinPublico();
            }
        });

        botonAtras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finishActivity(0);
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
            }
        });

        if (callback!=null) {
            callback.onAnswerLoaded();

            actualizarBotonesComodines();
        }


        return view;
    }
    public void setRespuestas (PreguntaJuego pregunta) {
        if (getContext()==null)
            return;

        respondido = false;
        this.pregunta=pregunta;
        textoComodin.setVisibility(View.GONE);

        String[] opciones = pregunta.getOpcionesRandomly();
        for (int i = 0; i < opciones.length; i++) {
            botones[i].setTextColor(getResources().getColor(R.color.colorPregunta));
            botones[i].setText(setCaracter(i)+ ": "+ opciones[i]);
            botones[i].setVisibility(View.VISIBLE);
        }

        for (int i = opciones.length; i < 4; i++) {
            botones[i].setVisibility(View.GONE);
        }
    }

    public void setTraduccion (Traduccion t, boolean traducir) {
        if (traducir) {
            String[] opciones = t.getOpciones();
            for (int i = 0; i < opciones.length; i++) {
                botones[i].setText(opciones[i]);
            }
        } else {
            String[] opciones = pregunta.getOpcionesRandomly(false);
            for (int i = 0; i < opciones.length; i++) {
                botones[i].setText(opciones[i]);
            }
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

    // COMODINES

    private void actualizarBotonesComodines() {
        comodines[0].setBackgroundResource(callback.hasUsedComodin(0)?R.drawable.comodin_llamada_off:R.drawable.comodin_llamada_on);
        comodines[1].setBackgroundResource(callback.hasUsedComodin(1)?R.drawable.comodin_mitad_off:R.drawable.comodin_mitad_on);
        comodines[2].setBackgroundResource(callback.hasUsedComodin(2)?R.drawable.comodin_publico_off:R.drawable.comodin_publico_on);
    }

    private void comodinLlamar() {
        if (callback==null || callback.hasUsedComodin(0)) {
            Toast.makeText(this.getContext(), getString(R.string.usarComodin), Toast.LENGTH_SHORT).show();
            return;
        }

        double minimo = 0;
        double maximo;
        switch (pregunta.getDificultad()) {
            case Easy:
                minimo=0.6;  // Conseguirá de base un minimo del 60%
                break;
            case Medium:
                minimo=0.4;  // Conseguirá de base un minimo del 40%
                break;
            case Hard:
                minimo=0.2;  // Conseguirá de base un minimo del 20%
                break;
            default:
                break;
        }


        CATEGORIA cat = pregunta.getCategoria();
        CATEGORIA[] categorias = CATEGORIA.values();

        for (int i = 0; i < 3; i++) {
            // Si sabe del tema aumenta un 20% la probabilidad de conseguirlo
            if (cat.getID() == categorias[(int)(Math.random()*categorias.length)].getID()) {
                minimo+=0.2;
            }

            // Si no sabe del tema disminuye un 20%
            if (cat.getID() == categorias[(int)(Math.random()*categorias.length)].getID()) {
                minimo-=0.2;
            }
        }
        maximo = 1 - minimo;

        // Si está al 100% convencido dice la respuesta
        if (minimo>1) {
            ponerRespuesta(getString(R.string.llamadaSeguro), pregunta.getCorrectAnswer());
        } else{
            // Si no lo está podrá acertar, si no dirá una al azar (Puede acertar la random!!)
            boolean acertado = minimo>Math.random();
            ponerRespuesta(getString(R.string.llamadaCreo), (acertado) ? pregunta.getCorrectAnswer() : (int) (Math.random() * 4));
        }

        if (callback!=null) {
            callback.onComodinUsado(0);
            actualizarBotonesComodines();
        }
    }

    private void ponerRespuesta (String text, int respuesta) {
        char valor=setCaracter(respuesta);
        textoComodin.setText(text+valor);
        textoComodin.setVisibility(View.VISIBLE);
    }

    private char setCaracter (int pos) {
        switch (pos){
            case 0:
                return 'A';
            case 1:
                return 'B';
            case 2:
                return 'C';
            case 3:
                return 'D';
        }

        return 'A';
    }

    private void comodinMitad () {
        if (callback==null || callback.hasUsedComodin(1)) {
            Toast.makeText(this.getContext(), getString(R.string.usarComodin), Toast.LENGTH_SHORT).show();
            return;
        }
        int correct = pregunta.getCorrectAnswer();

        int another = -1;

        do {
            another = (int) (Math.random() * 4);
        } while (correct==another);

        for (int i = 0; i < botones.length; i++) {
            if (i!=correct && i!=another)
                botones[i].setVisibility(View.GONE);
        }

        if (callback!=null) {
            callback.onComodinUsado(1);
            actualizarBotonesComodines();
        }
    }

    private void comodinPublico () {
        if (callback==null || callback.hasUsedComodin(2)) {
            Toast.makeText(this.getContext(), getString(R.string.usarComodin), Toast.LENGTH_SHORT).show();
            return;
        }

        double minimo = 0;
        double maximo;
        switch (pregunta.getDificultad()) {
            // El mínimo no significa que lo conseguirá, solo que se hará más probable de conseguir
            // por el público, luego se hará reglas de 3 para reducir o aumentar la probabilidad
            case Easy:
                minimo=2;  // Conseguirá de base un minimo del 200%
                break;
            case Medium:
                minimo=1;  // Conseguirá de base un minimo del 100%
                break;
            case Hard:
                minimo=0.25;  // Conseguirá de base un minimo del 25%
                break;
            default:
                break;
        }
        maximo = 1 - minimo;
        if (maximo<0)
            maximo=0;

        double [] probabilidad = new double[4];
        int correctAnswer = pregunta.getCorrectAnswer();
        double suma = 0;
        for (int i = 0; i < probabilidad.length; i++) {
            if (i==correctAnswer) {
                probabilidad[i] =(Math.random() * (maximo - minimo)) + minimo;
            } else {
                probabilidad[i] = Math.random() * 0.5;
            }

            suma+=probabilidad[i];
        }

        for (int i = 0; i < probabilidad.length; i++) {
            probabilidad[i] = probabilidad[i]/suma;
        }

        DecimalFormat format = new DecimalFormat("###.##%");

        for (int i = 0; i < botones.length; i++) {
            botones[i].setText(botones[i].getText() + " >> " + format.format(probabilidad[i]));
        }

        if (callback!=null) {
            callback.onComodinUsado(2);
            actualizarBotonesComodines();
        }
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

        // Comodines
        boolean hasUsedComodin (int comodin);
        void onComodinUsado(int comodin);
    }
}
