package com.garcia.adrian.triviaapp.fragments;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.garcia.adrian.triviaapp.R;
import com.garcia.adrian.triviaapp.model.juego.PreguntaJuego;

public class FragmentJuegoPregunta extends Fragment {

    // Durante las preguntas
    private LinearLayout layoutPreguntas;
    private TextView textoTotalPuntos;
    private TextView textoNumeroPreguntas;
    private TextView textoEnunciado;
    private TextView textoPuntos;
    private TextView textoCategoria;
    private TextView textoDificultad;

    // Fin de la partidan
    private LinearLayout layoutGameOver;
    private TextView textoTotalAcertadasGameOver;
    private TextView textoTotalPuntosGameOver;

    private PreguntaJuego pregunta;
    private int antiguosPuntos;

    private OnQuestionSend callback;

    public FragmentJuegoPregunta() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_pregunta, container, false);

        // Partida
        layoutPreguntas = view.findViewById(R.id.fragmentLinearPregunta);
        textoTotalPuntos = view.findViewById(R.id.fragmentPreguntaTotalPuntos);
        textoNumeroPreguntas = view.findViewById(R.id.fragmentPreguntaNumero);
        textoEnunciado = view.findViewById(R.id.fragmentPreguntaEnunciado);
        textoPuntos = view.findViewById(R.id.fragmentPreguntaPuntos);
        textoCategoria = view.findViewById(R.id.fragmentPreguntaCategoria);
        textoDificultad = view.findViewById(R.id.fragmentPreguntaDificultad);

        // Game Over
        layoutGameOver = view.findViewById(R.id.fragmentLinearGameOver);
        textoTotalAcertadasGameOver = view.findViewById(R.id.fragmentPreguntaAcertadasFinal);
        textoTotalPuntosGameOver = view.findViewById(R.id.fragmentPreguntaPuntosFinal);

        // Lo hago desaparecer hasta tener alguna pregunta
        layoutPreguntas.setVisibility(View.GONE);
        layoutGameOver.setVisibility(View.GONE);

        if (callback!=null)
            callback.onQuestionLoaded();

        return view;
    }

    public void animacion (boolean acertado){
        // TODO:  Cambiar los parámetros de entrada
        new EsperarAsyncTask().execute(antiguosPuntos, antiguosPuntos+pregunta.getDificultad().getPuntuacion());
    }

    public void setPregunta (PreguntaJuego pregunta, int puntosActuales, int noPregunta, int totalPreguntas) {
        this.pregunta = pregunta;
        this.antiguosPuntos=puntosActuales;

        textoTotalPuntos.setText(puntosActuales+"");
        textoNumeroPreguntas.setText((noPregunta+1)+"/"+totalPreguntas);
        textoEnunciado.setText(pregunta.getEnunciado());
        textoPuntos.setText(pregunta.getDificultad().getPuntuacion()+"");
        textoCategoria.setText(pregunta.getCategoria().getName(getContext()));
        textoDificultad.setText(pregunta.getDificultad().getName(getContext()));
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            callback = (OnQuestionSend) context;
        }catch (ClassCastException e){
            System.out.println("Error: deberia implementar la interfaz");
        }
    }

    public interface OnQuestionSend {
        void onQuestionLoaded();
        void nextAnswer();
    }

    // Muestra el LinearLayout de las preguntas
    public void iniciarPartida() {
        layoutPreguntas.setVisibility(View.VISIBLE);
    }

    // Muestra el LinearLayout de fin de partida
    public void finalizarPartida(int acertadas, int totalPreguntas, int puntosTotales) {
        layoutPreguntas.setVisibility(View.GONE);
        layoutGameOver.setVisibility(View.VISIBLE);

        textoTotalAcertadasGameOver.setText(acertadas+"");
        textoTotalPuntosGameOver.setText(puntosTotales+"/"+totalPreguntas);
    }

    // Esperar 1 segundo cada vez se resuelva una pregunta para mostrar si la ha acertado o no
    private class EsperarAsyncTask extends AsyncTask<Integer, Void, Void> {
        private int puntos;

        protected Void doInBackground(Integer... puntuacion) {
            try {
                Thread.sleep(1000);
                if (puntuacion.length<2) {
                    return null;
                }
                /* No deja hacer animaciones directamente por código
                float deltaTime = 0.02f;   // Cuantos ms se dormirá cada vez que haga un Thread.sleep. 0.02 son 50 actualizaciones en 1 segundo
                long espera = (long) (deltaTime*1000);
                float velocidad = 0.75f;    // Velocidad de la animación, a más valor, más rapido irá
                float lerp = 0;         // Valor de la interpolación.

                float pIniciales = puntuacion[0];
                float pFinales = puntuacion[1];

                while (lerp<1) {
                    modificarTextoPuntuacion(Math.round(pIniciales + (pFinales-pIniciales)*lerp));

                    Thread.sleep(espera);
                    lerp += deltaTime*velocidad;
                }
                modificarTextoPuntuacion(Math.round(pFinales));*/
                this.puntos = puntuacion[1];

            } catch (InterruptedException ie) {
                Log.e("ERROR_INTERRUPCION",ie.getMessage());
            }

            return null;
        }

        protected void onPostExecute(Void v) {
            textoTotalPuntos.setText(this.puntos+"");
            if (callback!=null)
                callback.nextAnswer();
        }
    }
}