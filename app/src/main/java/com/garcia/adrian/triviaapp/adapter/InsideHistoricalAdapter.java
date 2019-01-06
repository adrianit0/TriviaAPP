package com.garcia.adrian.triviaapp.adapter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.garcia.adrian.triviaapp.R;
import com.garcia.adrian.triviaapp.model.historial.Pregunta;

import java.util.List;

public class InsideHistoricalAdapter extends RecyclerView.Adapter<InsideHistoricalAdapter.ViewHolder> {
    private List<Pregunta> historials;
    private Activity activity;

    public InsideHistoricalAdapter(List<Pregunta> historials, Activity activity) {
        this.historials = historials;
        this.activity = activity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(activity).inflate(R.layout.inside_historical_row, viewGroup, false);
        ViewHolder viewHolder= new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        final Pregunta pregunta = historials.get(i);

        boolean acertada = pregunta.getTuRespuesta().equals(pregunta.getRespuestaCorrecta());

        viewHolder.textQuestion.setText(pregunta.getNombrePregunta());
        viewHolder.textYourAnswer.setText(pregunta.getTuRespuesta());
        viewHolder.textYourAnswer.setTextColor(activity.getResources().getColor((acertada)?R.color.colorRespuestaCorrecta:R.color.colorRespuestaIncorrecta));
        viewHolder.textCorrectAnswer.setText(pregunta.getRespuestaCorrecta());
        viewHolder.textPuntuation.setText((acertada ? pregunta.getPuntos() : 0)+"");
    }

    @Override
    public int getItemCount() {
        return historials.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView textQuestion;
        private TextView textYourAnswer;
        private TextView textCorrectAnswer;
        private TextView textPuntuation;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            textQuestion= itemView.findViewById(R.id.textViewPregunta);
            textYourAnswer = itemView.findViewById(R.id.textViewTuRespuesta);
            textCorrectAnswer = itemView.findViewById(R.id.textViewRespuestaCorrecta);
            textPuntuation = itemView.findViewById(R.id.textViewPuntuacion);
        }
    }
}
