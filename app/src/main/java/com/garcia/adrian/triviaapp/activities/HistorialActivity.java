package com.garcia.adrian.triviaapp.activities;

import android.app.Activity;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.garcia.adrian.triviaapp.R;
import com.garcia.adrian.triviaapp.adapter.InsideHistoricalAdapter;
import com.garcia.adrian.triviaapp.adapter.ModoJuegoAdapter;
import com.garcia.adrian.triviaapp.enums.CATEGORIA;
import com.garcia.adrian.triviaapp.model.historial.HistorialPreguntasViewModel;
import com.garcia.adrian.triviaapp.model.historial.Pregunta;
import com.garcia.adrian.triviaapp.model.menu.ModoJuego;

import java.util.ArrayList;
import java.util.Arrays;

public class HistorialActivity extends AppCompatActivity {
    private InsideHistoricalAdapter mAdapter;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historial);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView= findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setHasFixedSize(true);

        int id = 1;
        Intent intent = getIntent();
        if (intent!=null) {
            id = Integer.parseInt(intent.getStringExtra("historial"));
        }

        final Activity activity = this;


        ViewModelProviders.of(this).get(HistorialPreguntasViewModel.class).getPreguntas(id, new HistorialPreguntasViewModel.OnGetQuery(){
            @Override
            public void onGetPreguntas(Pregunta[] preguntas) {
                mAdapter = new InsideHistoricalAdapter(Arrays.asList(preguntas), activity);

                recyclerView.setAdapter(mAdapter);
            }
        });
    }
}
