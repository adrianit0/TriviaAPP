package com.garcia.adrian.triviaapp.fragments;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.garcia.adrian.triviaapp.R;
import com.garcia.adrian.triviaapp.adapter.ModoHistorialAdapter;
import com.garcia.adrian.triviaapp.data.AppDatabase;
import com.garcia.adrian.triviaapp.model.historial.HistorialPartidaViewModel;
import com.garcia.adrian.triviaapp.model.historial.Partida;
import com.garcia.adrian.triviaapp.model.historial.Pregunta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HistoryFragment extends Fragment {

    private ModoHistorialAdapter mAdapter;
    private ArrayList<Partida> partidas;

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;

    private HistorialPartidaViewModel model;

    public HistoryFragment() {
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

        partidas = new ArrayList<>();

        model = ViewModelProviders.of (this).get(HistorialPartidaViewModel.class);
        model.getPartidas().observe(this, new Observer<List<Partida>>() {
            @Override
            public void onChanged(List<Partida> listaPartidas) {
                partidas.addAll(listaPartidas);
                mAdapter = new ModoHistorialAdapter(partidas, R.layout.history_row, getActivity(), null);

                recyclerView.setAdapter(mAdapter);
            }
        });

        //TODO: Poner el Listener


        return rootView;
    }
}
