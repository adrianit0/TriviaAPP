package com.garcia.adrian.triviaapp.adapter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.garcia.adrian.triviaapp.R;
import com.garcia.adrian.triviaapp.model.historial.Partida;

import java.util.List;

public class HistorialAdapter extends RecyclerView.Adapter<HistorialAdapter.ViewHolder> {
    private List<Partida> historials;
    private int layout;
    private Activity activity;
    private HistorialAdapter.OnItemClickListener listener;

    public HistorialAdapter(List<Partida> historials, int layout, Activity activity, HistorialAdapter.OnItemClickListener listener) {
        this.historials = historials;
        this.layout = layout;
        this.activity = activity;
        this.listener = listener;
    }

    @NonNull
    @Override
    public HistorialAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(activity).inflate(layout, viewGroup, false);
        HistorialAdapter.ViewHolder viewHolder= new HistorialAdapter.ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull HistorialAdapter.ViewHolder viewHolder, int i) {
        final Partida historial = historials.get(i);

        viewHolder.textTitulo.setText(activity.getString(R.string.tituloHistorial) + " #"+ historial.getTitulo());

        viewHolder.textPuntuacion.setText(historial.getPuntuacion()+"");
        viewHolder.textCategoria.setText(historial.getCategoria().getName(activity.getApplicationContext()));
        viewHolder.textAcertadas.setText(historial.getAcertadas()+"");
        viewHolder.textFecha.setText(historial.getFecha().toString());

        viewHolder.linear.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                listener.onItemClick(v, historial);
            }
        });
    }

    @Override
    public int getItemCount() {
        return historials.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout linear;
        private TextView textTitulo;
        private TextView textPuntuacion;
        private TextView textAcertadas;
        private TextView textCategoria;
        private TextView textFecha;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            textTitulo= itemView.findViewById(R.id.textViewTitulo);
            textPuntuacion = itemView.findViewById(R.id.textViewPuntuacion);
            textAcertadas = itemView.findViewById(R.id.textViewAcertadas);
            textCategoria = itemView.findViewById(R.id.textViewCategoria);
            textFecha = itemView.findViewById(R.id.textViewFecha);

            linear = itemView.findViewById(R.id.linear);
        }
    }

    public interface OnItemClickListener{
        void onItemClick(View vista, Partida modo);
    }

}
