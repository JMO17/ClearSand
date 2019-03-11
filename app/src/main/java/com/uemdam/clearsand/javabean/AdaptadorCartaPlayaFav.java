package com.uemdam.clearsand.javabean;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.uemdam.clearsand.R;

import java.util.ArrayList;

/**
 * Clase Adaptador que recibe los datos de la playa
 * para poder crear las cartas de la playa.
 */
public class AdaptadorCartaPlayaFav extends RecyclerView.Adapter<AdaptadorCartaPlayaFav.CartaPlayaViewHolder>
        implements View.OnClickListener{

    /*--------------------------------   ATRIBUTOS   ------------------------------------------*/
    public static final String MIS_FAVORITOS = "arhivofav";

    private ArrayList<Playa> datos;
    private View.OnClickListener listener;
    private Usuario[] user;

    /*--------------------------------    CONSTRUCTOR  ------------------------------------------*/
    public AdaptadorCartaPlayaFav(ArrayList<Playa> datos, Usuario[] user) {
        this.datos = datos;
        this.user = user;
    }

    /*--------------------------------   METODOS ADAPTER  -----------------------------------------*/
    @NonNull
    @Override
    public CartaPlayaViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_playa, viewGroup, false);
        v.setOnClickListener(this);
        CartaPlayaViewHolder cpvh = new CartaPlayaViewHolder(v,viewGroup.getContext(), user,this);
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
    /*--------------------------------   METODOS LISTENER -----------------------------------------*/
    @Override
    public void onClick(View v) {
        if(listener != null) {
            listener.onClick(v);
        }
    }

    public void setOnClickListener(View.OnClickListener listener) {
        this.listener = listener;
    }

    /*--------------------------------   CLASE INTERNA   ------------------------------------------*/
    /**
     * Clase que hereda de ViewHolder,
     * Establece los datos y la imagen de una playa en una carta
     */
    public static class CartaPlayaViewHolder extends RecyclerView.ViewHolder {

        /*Elementos que se ven en la carta Playa*/
        private ImageView ivFoto;
        private TextView tvDistancia;
        private TextView tvNombre;
        private ToggleButton tbFav;

        private AdaptadorCartaPlayaFav adap;
        private Context contexto;
        /*DATABASE*/
        private DatabaseReference dbR;
        Usuario[] user;
        ArrayList<String> favoritos;

        /**
         * Constructor
         * @param itemView
         */
        public CartaPlayaViewHolder(@NonNull View itemView, Context contexto, Usuario[] user, AdaptadorCartaPlayaFav adap) {
            super(itemView);

            this.adap = adap;

            ivFoto = itemView.findViewById(R.id.ivFotoCard);
            tvDistancia = itemView.findViewById(R.id.tvDistanciaCard);
            tvNombre = itemView.findViewById(R.id.tvNombreCard);
            tbFav = itemView.findViewById(R.id.tbFavCard);

            dbR = FirebaseDatabase.getInstance().getReference().child("usuarios");
            this.contexto = contexto;

            this.user = user;
            favoritos = user[0].getPlayasUsuarioFav();

        }

        /**
         * Recibe los datos de una playa y rellena los views de la carta
         * con esos datos
         * @param playa
         */
        public void bindPlaya(final Playa playa) {
            Glide.with(ivFoto.getContext()).load(playa.getUrlImagen()).into(ivFoto);
            tvDistancia.setText(playa.getCoordenada_geográfica_Latitud());
            tvNombre.setText(playa.getNombre());

            //Cargar toggle button con los favoritos del usuario
            if(favoritos.isEmpty()) {
                // SI NO HAY FAVORITOS -> Todos los FAV Desactivados
                tbFav.setChecked(false);
                tbFav.setBackgroundDrawable(ContextCompat.getDrawable(tbFav.getContext(), R.drawable.ic_me_gusta_boton));

            } else {
                if(favoritos.contains(playa.getId())) {
                    tbFav.setChecked(true);
                    tbFav.setBackgroundDrawable(ContextCompat.getDrawable(tbFav.getContext(), R.drawable.ic_me_gusta_boton_pressed));
                } else {
                    tbFav.setChecked(false);
                    tbFav.setBackgroundDrawable(ContextCompat.getDrawable(tbFav.getContext(), R.drawable.ic_me_gusta_boton));
                }
            }

            tbFav.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    // Quitar de los favoritos de usuario
                    user[0].getPlayasUsuarioFav().remove(playa.getId());
                    dbR.child(user[0].getKeyUsuario()).setValue(user[0]);
                    adap.removeAt(getAdapterPosition());
                }
            });

        }


    } // fin CartaPlayaViewHolder


    public void removeAt(int position) {
        datos.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, datos.size());
    }
}
