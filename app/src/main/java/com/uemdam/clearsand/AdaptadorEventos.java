package com.uemdam.clearsand;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.uemdam.clearsand.javabean.Evento;

import java.util.ArrayList;

public class AdaptadorEventos extends RecyclerView.Adapter<AdaptadorEventos.MiViewHolder> implements View.OnClickListener{
    private ArrayList<Evento> lista;
    private View.OnClickListener listener;




    public static class MiViewHolder extends RecyclerView.ViewHolder{
        private ImageView imagen;
        private TextView  nombrePlaya;
        private TextView fecha;
        private TextView descripcion;

        public MiViewHolder( View itemView) {
            super(itemView);
            imagen=itemView.findViewById(R.id.ivPlayaEv);
            nombrePlaya=itemView.findViewById(R.id.tvNomPlaya);
            fecha=itemView.findViewById(R.id.tvFecha);
            descripcion=itemView.findViewById(R.id.tvDescEv);

        }

        public void bindItemListaEv(Evento evento){

            imagen.setImageResource(evento.getImagen());
            nombrePlaya.setText(evento.getPlayaEvento().getNombrePlaya());
            fecha.setText(evento.getFechaEvento());
            descripcion.setText(evento.getDescripcion());
        }

    }

    @NonNull
    @Override
    public MiViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.rv_eventos,viewGroup,false);
        v.setOnClickListener(this);
        MiViewHolder vh= new MiViewHolder(v);

        return vh;
    }

    public AdaptadorEventos(ArrayList<Evento> lista) {
        this.lista = lista;
    }

    @Override
    public void onBindViewHolder(@NonNull MiViewHolder miViewHolder, int position) {
        miViewHolder.bindItemListaEv(lista.get(position));
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    public void setOnClickListener(View.OnClickListener listener) {
        this.listener = listener;
    }

    @Override
    public void onClick(View v) {
        if(listener!=null){
            listener.onClick(v);
        }

    }

}
