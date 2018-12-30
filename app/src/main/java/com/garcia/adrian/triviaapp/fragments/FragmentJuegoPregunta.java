package com.garcia.adrian.triviaapp.fragments;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Trace;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.garcia.adrian.triviaapp.R;
import com.garcia.adrian.triviaapp.model.juego.PreguntaJuego;

public class FragmentJuegoPregunta extends Fragment {

    private TextView textoTotalPuntos;
    private TextView textoNumeroPreguntas;
    private TextView textoEnunciado;
    private TextView textoPuntos;
    private TextView textoCategoria;
    private TextView textoDificultad;

    private PreguntaJuego pregunta;
    private int antiguosPuntos;

    private OnQuestionSend callback;

    public FragmentJuegoPregunta() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_pregunta, container, false);

        textoTotalPuntos = view.findViewById(R.id.fragmentPreguntaTotalPuntos);
        textoNumeroPreguntas = view.findViewById(R.id.fragmentPreguntaNumero);
        textoEnunciado = view.findViewById(R.id.fragmentPreguntaEnunciado);
        textoPuntos = view.findViewById(R.id.fragmentPreguntaPuntos);
        textoCategoria = view.findViewById(R.id.fragmentPreguntaCategoria);
        textoDificultad = view.findViewById(R.id.fragmentPreguntaDificultad);

        if (callback!=null)
            callback.onQuestionChange();

        return view;
    }

    public void animacion (boolean acertado){
        // TODO:  Cambiar los parámetros de entrada
        new EsperarAsyncTask().execute(antiguosPuntos, antiguosPuntos+pregunta.getDificultad().getPuntuacion());
    }

    public void setPregunta (PreguntaJuego pregunta, int puntosActuales, int noPregunta) {
        this.pregunta = pregunta;
        this.antiguosPuntos=puntosActuales;

        textoTotalPuntos.setText(puntosActuales+"");
        textoNumeroPreguntas.setText((noPregunta+1)+"/10");
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
        void onQuestionChange();
        void nextAnswer();
    }

    private class EsperarAsyncTask extends AsyncTask<Integer, Void, Void> {

        private int puntos;

        protected Void doInBackground(Integer... puntuacion) {
            try {
                Thread.sleep(1000);
                if (puntuacion.length<2) {
                    return null;
                }
                /* No deja hacer animaciones
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