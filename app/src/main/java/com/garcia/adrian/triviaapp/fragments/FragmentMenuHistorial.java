package com.garcia.adrian.triviaapp.fragments;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.garcia.adrian.triviaapp.R;
import com.garcia.adrian.triviaapp.activities.HistorialActivity;
import com.garcia.adrian.triviaapp.adapter.HistorialAdapter;
import com.garcia.adrian.triviaapp.model.historial.HistorialPartidaViewModel;
import com.garcia.adrian.triviaapp.model.historial.Partida;

import java.util.ArrayList;
import java.util.List;

public class FragmentMenuHistorial extends Fragment {

    private HistorialAdapter mAdapter;
    private ArrayList<Partida> partidas;

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;

    private HistorialPartidaViewModel model;
    private OnHistoricalDataLoadedListener callbackData;
    private OnHistoricalFragmentLoadedListener callbackFragment;

    public FragmentMenuHistorial() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_lista, container, false);

        recyclerView= rootView.findViewById(R.id.recyclerView);
        layoutManager = new LinearLayoutManager(this.getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        partidas = new ArrayList<>();

        final FragmentMenuHistorial thisFragment = this;

        model = ViewModelProviders.of (this).get(HistorialPartidaViewModel.class);
        model.getPartidas().observe(this, new Observer<List<Partida>>() {
            @Override
            public void onChanged(List<Partida> listaPartidas) {
                partidas.addAll(listaPartidas);
                mAdapter = new HistorialAdapter(partidas, R.layout.history_row, getActivity(), new HistorialAdapter.OnItemClickListener(){
                    @Override
                    public void onItemClick(View vista, Partida partida) {
                        Intent intent = new Intent(getActivity(), HistorialActivity.class);
                        intent.putExtra("historial", partida.getId()+"");
                        startActivity(intent);
                    }
                });

                recyclerView.setAdapter(mAdapter);

                if (partidas!=null && callbackData !=null)
                    callbackData.OnHistoricalDataLoaded(partidas);

                if (callbackFragment!=null)
                    callbackFragment.onHistoricalFragmentLoadedListener(thisFragment);
            }
        });

        return rootView;
    }

    public void recibirCallback (OnHistoricalDataLoadedListener callback) {
        this.callbackData = callback;

        if (partidas!=null)
            callback.OnHistoricalDataLoaded(partidas);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            callbackFragment = (OnHistoricalFragmentLoadedListener) context;
        }catch (ClassCastException e){
            System.out.println("Error: deberia implementar la interfaz");
        }
    }

    public interface OnHistoricalDataLoadedListener {
        void OnHistoricalDataLoaded (List<Partida> partidas);
    }

    public interface OnHistoricalFragmentLoadedListener {
        void onHistoricalFragmentLoadedListener(FragmentMenuHistorial fragment);
    }
}
