package com.garcia.adrian.triviaapp.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.garcia.adrian.triviaapp.R;
import com.garcia.adrian.triviaapp.model.juego.PreguntaJuego;

public class FragmentJuegoRespuestas extends Fragment {

    private OnQuestionSend callback;

    public FragmentJuegoRespuestas() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_respuestas, container, false);


        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            callback = (FragmentJuegoRespuestas.OnQuestionSend) context;
        }catch (ClassCastException e){
            System.out.println("Error: deberia implementar la interfaz");
        }
    }

    public interface OnQuestionSend {
        void onChange ();
    }

}
