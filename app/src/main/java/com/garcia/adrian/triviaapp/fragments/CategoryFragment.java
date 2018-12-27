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

import com.garcia.adrian.triviaapp.Pregunta.CATEGORIA;
import com.garcia.adrian.triviaapp.R;
import com.garcia.adrian.triviaapp.adapter.ModoCategoryAdapter;
import com.garcia.adrian.triviaapp.model.ModoCategoria;

import java.util.ArrayList;

public class CategoryFragment extends Fragment {

    private ModoCategoryAdapter mAdapter;
    private ArrayList<ModoCategoria> categorias;

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private CategoryFragment.OnGameCategoryClickListener callback;


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
            categorias.add (new ModoCategoria(c.getName(this.getContext()), "0", "0"));

        // TODO: Poner el listener correcto
        mAdapter = new ModoCategoryAdapter(categorias, R.layout.category_row, getActivity(), null);

        recyclerView.setAdapter(mAdapter);

        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            callback = (CategoryFragment.OnGameCategoryClickListener) context;
        }catch (ClassCastException e){
            System.out.println("Error: deberia implementar la interfaz");
        }
    }

    public interface OnGameCategoryClickListener {
        void onCategoryClickListener (ModoCategoria modo);
    }
}
