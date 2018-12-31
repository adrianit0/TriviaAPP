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
import com.garcia.adrian.triviaapp.model.menu.ModoJuego;

import java.util.List;

public class ModoCategoryAdapter extends RecyclerView.Adapter<ModoCategoryAdapter.ViewHolder> {
    private List<ModoJuego> modoJuegos;
    private int layout;
    private Activity activity;
    private OnItemClickListener listener;

    //TODO: Mirar si sirve para algo el ViewGroup
    private ViewGroup viewGroup;

    public ModoCategoryAdapter(List<ModoJuego> modoJuegos, int layout, Activity activity, OnItemClickListener listener) {
        this.modoJuegos = modoJuegos;
        this.layout = layout;
        this.activity = activity;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ModoCategoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(activity).inflate(layout,viewGroup, false);
        ModoCategoryAdapter.ViewHolder viewHolder= new ModoCategoryAdapter.ViewHolder(view);

        this.viewGroup = viewGroup;

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ModoCategoryAdapter.ViewHolder viewHolder, int i) {
        final ModoJuego modo = modoJuegos.get(i);

        viewHolder.textTitulo.setText(modo.getTitulo());

        if (viewHolder.textMaxPuntuacion!=null)
            viewHolder.textMaxPuntuacion.setText(modo.getMaximaPuntuacion());

        if (viewHolder.textMaxAcertadas!=null)
            viewHolder.textMaxAcertadas.setText(modo.getTotalAcertadas());

        if (viewHolder.textDescripcion!=null)
            viewHolder.textDescripcion.setText(modo.getDescripcion());

        viewHolder.linear.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                listener.onItemClick(v, modo);
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
        private TextView textMaxPuntuacion;
        private TextView textMaxAcertadas;
        private TextView textDescripcion;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            textTitulo= itemView.findViewById(R.id.textViewTitulo);
            textMaxPuntuacion = itemView.findViewById(R.id.textViewMaxPuntuacion);
            textMaxAcertadas = itemView.findViewById(R.id.textViewMaxAcertadas);

            textDescripcion = itemView.findViewById(R.id.textViewDescripcion);

            linear = itemView.findViewById(R.id.linear);
        }
    }

    public interface OnItemClickListener{
        void onItemClick(View vista, ModoJuego modo);
    }
}
