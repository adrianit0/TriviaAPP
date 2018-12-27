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
import com.garcia.adrian.triviaapp.adapter.ModoHistorialAdapter;
import com.garcia.adrian.triviaapp.model.historial.Partida;

import java.util.ArrayList;
import java.util.Arrays;

public class HistoryFragment extends Fragment {

    private ModoHistorialAdapter mAdapter;
    private ArrayList<Partida> partidas;

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private HistoryFragment.OnHistoryClickListener callback;


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

        // TODO: Cargar desde base de datos ROOM
        partidas.addAll (
                Arrays.asList(
                        new Partida(getContext(), 0, "0", "0", "Any", "27/12/2018"),
                        new Partida(getContext(), 1, "0", "0", "Any", "27/12/2018"),
                        new Partida(getContext(), 2, "0", "0", "Any", "27/12/2018"),
                        new Partida(getContext(), 3, "0", "0", "Any", "27/12/2018"),
                        new Partida(getContext(), 4, "0", "0", "Any", "27/12/2018"),
                        new Partida(getContext(), 5, "0", "0", "Any", "27/12/2018"),
                        new Partida(getContext(), 6, "0", "0", "Any", "27/12/2018")
                )
        );

        // TODO: Poner el listener correcto
        mAdapter = new ModoHistorialAdapter(partidas, R.layout.history_row, getActivity(), null);

        recyclerView.setAdapter(mAdapter);

        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            callback = (HistoryFragment.OnHistoryClickListener) context;
        }catch (ClassCastException e){
            System.out.println("Error: deberia implementar la interfaz");
        }
    }

    public interface OnHistoryClickListener {
        void onHistoryClickListener (Partida modo);
    }
}
