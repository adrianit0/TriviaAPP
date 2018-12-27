package com.garcia.adrian.triviaapp.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.garcia.adrian.triviaapp.R;
import com.garcia.adrian.triviaapp.adapter.ModoJuegoAdapter;
import com.garcia.adrian.triviaapp.model.ModoJuego;

import java.util.ArrayList;
import java.util.Arrays;

public class ModeFragment extends Fragment {

    private ModoJuegoAdapter mAdapter;
    private ArrayList<ModoJuego> modoJuegos;

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private OnGameModeClickListener callback;


    public ModeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_lista, container, false);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView= rootView.findViewById(R.id.recyclerView);
        layoutManager = new LinearLayoutManager(this.getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        modoJuegos = new ArrayList<>();
        modoJuegos.addAll (
                Arrays.asList(
                        new ModoJuego (getString(R.string.modoJuegoTitulo1), getString(R.string.modoJuegoDescripcion1)),
                        new ModoJuego (getString(R.string.modoJuegoTitulo2), getString(R.string.modoJuegoDescripcion2)),
                        new ModoJuego (getString(R.string.modoJuegoTitulo3), getString(R.string.modoJuegoDescripcion3)),
                        new ModoJuego (getString(R.string.modoJuegoTitulo4), getString(R.string.modoJuegoDescripcion4)),
                        new ModoJuego (getString(R.string.modoJuegoTitulo5), getString(R.string.modoJuegoDescripcion5)),
                        new ModoJuego (getString(R.string.modoJuegoTitulo6), getString(R.string.modoJuegoDescripcion6)),
                        new ModoJuego (getString(R.string.modoJuegoTitulo7), getString(R.string.modoJuegoDescripcion7)),
                        new ModoJuego (getString(R.string.modoJuegoTitulo8), getString(R.string.modoJuegoDescripcion8))
                )
        );

        // TODO: Poner el listener correcto
        mAdapter = new ModoJuegoAdapter(modoJuegos, R.layout.mode_row, getActivity(), null);

        recyclerView.setAdapter(mAdapter);

        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            callback = (OnGameModeClickListener) context;
        }catch (ClassCastException e){
            System.out.println("Error: deberia implementar la interfaz");
        }
    }

    public interface OnGameModeClickListener {
        void onModeClickListener (ModoJuego modo);
    }
}