package com.garcia.adrian.triviaapp.fragments;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.garcia.adrian.triviaapp.activities.GameActivity;
import com.garcia.adrian.triviaapp.enums.CATEGORIA;
import com.garcia.adrian.triviaapp.R;
import com.garcia.adrian.triviaapp.adapter.ModoCategoryAdapter;
import com.garcia.adrian.triviaapp.model.juego.PreguntaJuego;
import com.garcia.adrian.triviaapp.model.juego.PreguntaJuegoViewModel;
import com.garcia.adrian.triviaapp.model.menu.ModoCategoria;

import java.util.ArrayList;
import java.util.List;

public class CategoryFragment extends Fragment {

    private ModoCategoryAdapter mAdapter;
    private ArrayList<ModoCategoria> categorias;

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;


    public CategoryFragment() {
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
            categorias.add (new ModoCategoria(c.getName(this.getContext()), c,"0", "0"));

        mAdapter = new ModoCategoryAdapter(categorias, R.layout.category_row, getActivity(), new ModoCategoryAdapter.OnItemClickListener(){

            @Override
            public void onItemClick(View vista, ModoCategoria modo) {
                Intent intent = new Intent(getActivity(), GameActivity.class);
                intent.putExtra("modoJuego", modo);
                startActivity(intent);
            }
        });

        recyclerView.setAdapter(mAdapter);

        return rootView;
    }
}