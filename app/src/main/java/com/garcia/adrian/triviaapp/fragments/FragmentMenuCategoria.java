package com.garcia.adrian.triviaapp.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
import com.garcia.adrian.triviaapp.model.historial.Partida;
import com.garcia.adrian.triviaapp.model.menu.ModoJuego;

import java.util.ArrayList;
import java.util.List;

public class FragmentMenuCategoria extends Fragment {

    private ModoJuegoAdapter mAdapter;
    private ArrayList<ModoJuego> categorias;

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private onFragmentLoadedListener callback;


    public FragmentMenuCategoria() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_lista, container, false);

        recyclerView= rootView.findViewById(R.id.recyclerView);
        layoutManager = new LinearLayoutManager(this.getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        categorias = new ArrayList<>();

        // Creamos todas las categorias
        CATEGORIA[] listaCategoria = CATEGORIA.values();
        for (CATEGORIA c : listaCategoria) {
            categorias.add (new ModoJuego(c.getName(getContext()), c, "-", "-"));
        }

        mAdapter = new ModoJuegoAdapter(categorias, R.layout.category_row, getActivity(), new ModoJuegoAdapter.OnItemClickListener(){
            @Override
            public void onItemClick(View vista, ModoJuego modo) {
                Intent intent = new Intent(getActivity(), GameActivity.class);
                intent.putExtra("modoJuego", modo);
                startActivity(intent);
            }
        });

        recyclerView.setAdapter(mAdapter);

        // Cuando se cargue las preguntas del ROOM, colocar las máximas puntuaciones y cantidad de preguntas acertadas
        // de cada categoría.
        callback.onFragmentLoaded(new FragmentMenuHistorial.OnHistoricalDataLoadedListener() {
            @Override
            public void OnHistoricalDataLoaded(List<Partida> partidas) {
                // Añadimos todas las categorias
                for (ModoJuego m : categorias) {
                    int maximaPuntuacion=0;
                    int totalAcertadas=0;
                    for (Partida p : partidas) {
                        CATEGORIA c = m.getCategoria();
                        if (p.getCategoria()==c) {
                            totalAcertadas+=p.getAcertadas();
                            if (p.getPuntuacion()>maximaPuntuacion)
                                maximaPuntuacion=p.getPuntuacion();
                        }
                    }

                    m.setMaximaPuntuacion(maximaPuntuacion+"");
                    m.setTotalAcertadas(totalAcertadas+"");
                }

                mAdapter.notifyDataSetChanged();
            }
        });

        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            callback = (onFragmentLoadedListener) context;
        }catch (ClassCastException e){
            System.out.println("Error: deberia implementar la interfaz");
        }
    }

    public interface onFragmentLoadedListener {
        void  onFragmentLoaded (FragmentMenuHistorial.OnHistoricalDataLoadedListener callback);
    }
}