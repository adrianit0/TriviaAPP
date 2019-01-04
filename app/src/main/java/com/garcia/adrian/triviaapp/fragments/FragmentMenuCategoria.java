package com.garcia.adrian.triviaapp.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.garcia.adrian.triviaapp.activities.GameActivity;
import com.garcia.adrian.triviaapp.enums.CATEGORIA;
import com.garcia.adrian.triviaapp.R;
import com.garcia.adrian.triviaapp.adapter.ModoJuegoAdapter;
import com.garcia.adrian.triviaapp.model.menu.ModoJuego;

import java.util.ArrayList;

public class FragmentMenuCategoria extends Fragment {

    private ModoJuegoAdapter mAdapter;
    private ArrayList<ModoJuego> categorias;

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;


    public FragmentMenuCategoria() {
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

        categorias = new ArrayList<>();
        CATEGORIA[] listaCategoria = CATEGORIA.values();

        // Añadimos todas las categorias
        for (CATEGORIA c : listaCategoria)
            categorias.add (new ModoJuego(c.getName(this.getContext()), c,"0", "0"));

        mAdapter = new ModoJuegoAdapter(categorias, R.layout.category_row, getActivity(), new ModoJuegoAdapter.OnItemClickListener(){
            //TODO: Añadir las máximas puntuaciones y cantidad de acertadas de cada categoria con ROOM
            @Override
            public void onItemClick(View vista, ModoJuego modo) {
                Intent intent = new Intent(getActivity(), GameActivity.class);
                intent.putExtra("modoJuego", modo);
                startActivity(intent);
            }
        });

        recyclerView.setAdapter(mAdapter);

        return rootView;
    }
}