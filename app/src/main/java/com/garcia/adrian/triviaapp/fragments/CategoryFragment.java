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

    private ModoCategoria categoriaElegida=null;


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
                // Si hemos escogido un modo no permitiremos que lo vuelva a escoger otro hasta haber entrado
                if (categoriaElegida!=null) {
                    return;
                }
                categoriaElegida = modo;


                // Carga las preguntas antes de empezar la partida para no tener que cargarla dentro

                ConnectivityManager connMgr = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);

                NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

                if (networkInfo != null && networkInfo.isConnected()) {
                    PreguntaJuegoViewModel model = ViewModelProviders.of(getActivity()).get(PreguntaJuegoViewModel.class);

                    model.getPreguntas(modo).observe(getActivity(), new Observer<List<PreguntaJuego>>() {
                        @Override
                        public void onChanged(List<PreguntaJuego> preguntas) {
                            // Cuando esté cargado liberamos la categoria elegida y nos vamos al otro activity
                            categoriaElegida = null;
                            Intent intent = new Intent(getActivity(), GameActivity.class);

                            startActivity(intent);
                        }
                    });

                } else {
                    (Toast.makeText(getActivity(), R.string.no_internet_connection, Toast.LENGTH_LONG)).show();
                }
            }
        });

        recyclerView.setAdapter(mAdapter);

        return rootView;
    }
}