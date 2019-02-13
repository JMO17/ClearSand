package com.example.clearsand.javabeans;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.clearsand.R;

import java.util.ArrayList;

/**
 * Clase Adaptador que recibe los datos de la playa
 * para poder crear las cartas de la playa.
 */
public class AdaptadorCartaPlaya extends RecyclerView.Adapter<AdaptadorCartaPlaya.CartaPlayaViewHolder> {

    private ArrayList<Playa> datos;

    public AdaptadorCartaPlaya(ArrayList<Playa> datos) {
        this.datos = datos;
    }

    @NonNull
    @Override
    public CartaPlayaViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_playa, viewGroup, false);
        CartaPlayaViewHolder cpvh = new CartaPlayaViewHolder(v,viewGroup.getContext());
        return cpvh;
    }

    @Override
    public void onBindViewHolder(@NonNull CartaPlayaViewHolder cartaPlayaViewHolder, int i) {
        cartaPlayaViewHolder.bindPlaya(datos.get(i));
    }

    @Override
    public int getItemCount() {
        return datos.size();
    }

    /**
     * Clase que hereda de ViewHolder,
     * Establece los datos y la imagen de una playa en una carta
     */
    public static class CartaPlayaViewHolder extends RecyclerView.ViewHolder {

        /*Elementos que se ven en la carta Playa*/
        private ImageView ivFoto;
        private TextView tvDistancia;
        private TextView tvNombre;

        private Context contexto;

        /**
         * Constructor
         * @param itemView
         */
        public CartaPlayaViewHolder(@NonNull View itemView, Context contexto) {
            super(itemView);
            ivFoto = itemView.findViewById(R.id.ivFotoCard);
            tvDistancia = itemView.findViewById(R.id.tvDistanciaCard);
            tvNombre = itemView.findViewById(R.id.tvNombreCard);
            this.contexto = contexto;
        }

        /**
         * Recibe los datos de una playa y rellena los views de la carta
         * con esos datos
         * @param playa
         */
        public void bindPlaya(Playa playa) {
            //TODO buscar la imagen en FIREBASE
            tvDistancia.setText(String.format(contexto.getString(R.string.tv_distancia_card), playa.getDistancia()));
            tvNombre.setText(playa.getNombre());
        }
    } // fin CartaPlayaViewHolder
}
