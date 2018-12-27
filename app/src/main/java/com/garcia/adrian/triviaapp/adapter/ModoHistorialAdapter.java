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
import com.garcia.adrian.triviaapp.model.HistorialPartida;

import java.util.List;

public class ModoHistorialAdapter extends RecyclerView.Adapter<ModoHistorialAdapter.ViewHolder> {
    private List<HistorialPartida> historials;
    private int layout;
    private Activity activity;
    private ModoHistorialAdapter.OnItemClickListener listener;

    private ViewGroup viewGroup;

    public ModoHistorialAdapter(List<HistorialPartida> historials, int layout, Activity activity, ModoHistorialAdapter.OnItemClickListener listener) {
        this.historials = historials;
        this.layout = layout;
        this.activity = activity;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ModoHistorialAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(activity).inflate(layout,viewGroup, false);
        ModoHistorialAdapter.ViewHolder viewHolder= new ModoHistorialAdapter.ViewHolder(view);

        this.viewGroup = viewGroup;

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ModoHistorialAdapter.ViewHolder viewHolder, int i) {
        final HistorialPartida historial = historials.get(i);

        viewHolder.textTitulo.setText(historial.getTitulo());
        viewHolder.textPuntuacion.setText(historial.getPuntuacion());
        viewHolder.textCategoria.setText(historial.getCategoria());
        viewHolder.textAcertadas.setText(historial.getAcertadas());
        viewHolder.textFecha.setText(historial.getFecha());

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
        void onItemClick(View vista, HistorialPartida modo);
    }

}
