package com.uemdam.clearsand;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.uemdam.clearsand.javabean.Evento;

import java.util.ArrayList;
import java.util.List;

public class  AdaptadorEventos extends RecyclerView.Adapter<AdaptadorEventos.MyViewHolder>{

    Context context;
    List<Evento> lista;

    public AdaptadorEventos(Context context, List<Evento> lista) {
        this.context = context;
        this.lista = lista;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v;
        v=LayoutInflater.from(context).inflate(R.layout.rv_eventos,parent,false);
        MyViewHolder vh= new MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.tvNomEv.setText(lista.get(position).getNombreEvento());
        holder.tvNomPlaya.setText(lista.get(position).getPlayaEvento().getNombrePlaya());
        holder.tvFecha.setText(lista.get(position).getFechaEvento());

    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    public static  class MyViewHolder extends RecyclerView.ViewHolder{

        private TextView tvNomEv;
        private TextView tvFecha;
        private TextView tvNomPlaya;
        private ImageView ivEv;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            tvNomEv= itemView.findViewById(R.id.tvNomEv);
            tvNomPlaya=itemView.findViewById(R.id.tvNomPlaya);
            tvFecha=itemView.findViewById(R.id.tvFecha);
            ivEv=itemView.findViewById(R.id.ivPlayaEv);
        }
    }




}
