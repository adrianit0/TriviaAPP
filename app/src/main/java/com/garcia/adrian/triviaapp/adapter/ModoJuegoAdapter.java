package com.garcia.adrian.triviaapp.adapter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.garcia.adrian.triviaapp.model.menu.ModoJuego;
import com.garcia.adrian.triviaapp.R;

import java.util.List;

public class ModoJuegoAdapter extends RecyclerView.Adapter<ModoJuegoAdapter.ViewHolder> {
    private List<ModoJuego> modoJuegos;
    private int layout;
    private Activity activity;
    private OnItemClickListener listener;


    public ModoJuegoAdapter(List<ModoJuego> modoJuegos, int layout, Activity activity, OnItemClickListener listener) {
        this.modoJuegos = modoJuegos;
        this.layout = layout;
        this.activity = activity;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ModoJuegoAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(activity).inflate(layout,viewGroup, false);
        ViewHolder viewHolder= new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ModoJuegoAdapter.ViewHolder viewHolder, int i) {
        final ModoJuego modoJuego = modoJuegos.get(i);

        viewHolder.textTitulo.setText(modoJuego.getTitulo());
        viewHolder.textViewDescripcion.setText(modoJuego.getDescripcion());

        viewHolder.linear.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                listener.onItemClick(v, modoJuego);
            }
        });
    }

    @Override
    public int getItemCount() {
        return modoJuegos.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout linear;
        private TextView textTitulo;
        private TextView textViewDescripcion;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            textTitulo= itemView.findViewById(R.id.textViewTitulo);
            textViewDescripcion= itemView.findViewById(R.id.textViewDescripcion);

            linear = itemView.findViewById(R.id.linear);

        }
    }

    public interface OnItemClickListener{
        void onItemClick(View vista, ModoJuego modo);
    }
}