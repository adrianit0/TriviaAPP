package com.garcia.adrian.triviaapp.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.garcia.adrian.triviaapp.R;
import com.garcia.adrian.triviaapp.model.juego.PreguntaJuego;

public class FragmentJuegoPregunta extends Fragment {

    private TextView textoTotalPuntos;
    private TextView textoEnunciado;
    private TextView textoPuntos;
    private TextView textoCategoria;
    private TextView textoDificultad;

    //TODO: Meter los botones

    private OnQuestionSend callback;

    public FragmentJuegoPregunta() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_pregunta, container, false);

        textoTotalPuntos = view.findViewById(R.id.fragmentPreguntaTotalPuntos);
        textoEnunciado = view.findViewById(R.id.fragmentPreguntaEnunciado);
        textoPuntos = view.findViewById(R.id.fragmentPreguntaPuntos);
        textoCategoria = view.findViewById(R.id.fragmentPreguntaCategoria);
        textoDificultad = view.findViewById(R.id.fragmentPreguntaDificultad);

        if (callback!=null)
            callback.onChange();

        return view;
    }

    public void setPregunta (PreguntaJuego pregunta) {
        textoTotalPuntos.setText("0");
        textoEnunciado.setText(pregunta.getEnunciado());
        textoPuntos.setText("0");
        textoCategoria.setText(pregunta.getCategoria());
        textoDificultad.setText(pregunta.getDificultad());

        //TODO: Configurar los botones
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
        void onChange ();
    }
}