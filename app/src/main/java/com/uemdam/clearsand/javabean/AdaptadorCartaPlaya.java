package com.uemdam.clearsand.javabean;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.uemdam.clearsand.R;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * Clase Adaptador que recibe los datos de la playa
 * para poder crear las cartas de la playa.
 */
public class AdaptadorCartaPlaya extends RecyclerView.Adapter<AdaptadorCartaPlaya.CartaPlayaViewHolder>
        implements View.OnClickListener{

    /*--------------------------------   ATRIBUTOS   ------------------------------------------*/
    public static final String MIS_FAVORITOS = "arhivofav";

    private ArrayList<Playa> datos;
    private View.OnClickListener listener;

    /*--------------------------------    CONSTRUCTOR  ------------------------------------------*/
    public AdaptadorCartaPlaya(ArrayList<Playa> datos) {
        this.datos = datos;
    }

    /*--------------------------------   METODOS ADAPTER  -----------------------------------------*/
    @NonNull
    @Override
    public CartaPlayaViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_playa, viewGroup, false);
        v.setOnClickListener(this);
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
        private ImageButton ibFavorito;
        private ToggleButton tbFav;

        private Context contexto;
        /*DATABASE*/
        private DatabaseReference dbR;

        /**
         * Constructor
         * @param itemView
         */
        public CartaPlayaViewHolder(@NonNull View itemView, Context contexto) {
            super(itemView);

            ivFoto = itemView.findViewById(R.id.ivFotoCard);
            tvDistancia = itemView.findViewById(R.id.tvDistanciaCard);
            tvNombre = itemView.findViewById(R.id.tvNombreCard);
            //ibFavorito = itemView.findViewById(R.id.ibFavorito);
            tbFav = itemView.findViewById(R.id.tbFav);

            this.contexto = contexto;

            /*DATABASE*/
            dbR = FirebaseDatabase.getInstance().getReference().child("usuarios");
        }

        /**
         * Recibe los datos de una playa y rellena los views de la carta
         * con esos datos
         * @param playa
         */
        public void bindPlaya(final Playa playa) {
            Glide.with(ivFoto.getContext()).load(playa.getUrlImagen()).into(ivFoto);
            /*tvDistancia.setText(String.format(contexto.getString(R.string.tv_distancia_card), playa.getDistancia()));
            */
            tvDistancia.setText(playa.getCoordenada_geográfica_Latitud());
            tvNombre.setText(playa.getNombre());

            // Miramos en preferencias los
            SharedPreferences preferencias = contexto.getSharedPreferences(MIS_FAVORITOS, Context.MODE_PRIVATE);
            Set<String> favoritos = preferencias.getStringSet("IDS", new HashSet<String>());
            System.out.println(preferencias.getAll());
            if(favoritos.contains(playa.getId())) {
                tbFav.setChecked(true);
                tbFav.setBackgroundDrawable(ContextCompat.getDrawable(tbFav.getContext(), R.drawable.ic_me_gusta_boton_pressed));
            } else {
                tbFav.setChecked(false);
                tbFav.setBackgroundDrawable(ContextCompat.getDrawable(tbFav.getContext(), R.drawable.ic_me_gusta_boton));
            }

            tbFav.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(isChecked) {
                        // Cambiamos el imagen a seleccionado
                        tbFav.setBackgroundDrawable(ContextCompat.getDrawable(tbFav.getContext(), R.drawable.ic_me_gusta_boton_pressed));

                        //Añadimos a preferencias el id de la playa
                        SharedPreferences preferencias = contexto.getSharedPreferences(MIS_FAVORITOS, Context.MODE_PRIVATE); //Archivo y modo
                        Set<String> itemId = preferencias.getStringSet("IDS", new HashSet<String>()); //Recogemos valores anteriores o si no hay, se usa un nuevo hashset
                        itemId.add(playa.getId());
                        preferencias.edit().putStringSet("IDS", itemId).commit(); //editamos las preferencias con el nuevo set/ o set modificado.
                        //TODO  mirar si es mejor apply en vez de commit
                        System.out.println(preferencias.getAll());
                    } else {
                        tbFav.setBackgroundDrawable(ContextCompat.getDrawable(tbFav.getContext(), R.drawable.ic_me_gusta_boton));

                        SharedPreferences preferencias = contexto.getSharedPreferences(MIS_FAVORITOS, Context.MODE_PRIVATE);
                        Set<String> itemId = preferencias.getStringSet("IDS", new HashSet<String>());
                        itemId.remove(playa.getId());
                        preferencias.edit().putStringSet("IDS", itemId).commit();
                        System.out.println(preferencias.getAll());
                    }
                }
            });
        }

        public void bindPlaya2(final Playa playa) {
            Glide.with(ivFoto.getContext()).load(playa.getUrlImagen()).into(ivFoto);
            tvDistancia.setText(playa.getCoordenada_geográfica_Latitud());
            tvNombre.setText(playa.getNombre());

            //Cargar toggle button con datos del usuario
            ArrayList<String> favoritos = new ArrayList<>();

            if(favoritos.contains(playa.getId())) {
                tbFav.setChecked(true);
                tbFav.setBackgroundDrawable(ContextCompat.getDrawable(tbFav.getContext(), R.drawable.ic_me_gusta_boton_pressed));
            } else {
                tbFav.setChecked(false);
                tbFav.setBackgroundDrawable(ContextCompat.getDrawable(tbFav.getContext(), R.drawable.ic_me_gusta_boton));
            }

            tbFav.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(isChecked) {
                        // Cambiamos el imagen a seleccionado
                        tbFav.setBackgroundDrawable(ContextCompat.getDrawable(tbFav.getContext(), R.drawable.ic_me_gusta_boton_pressed));

                    } else {
                        tbFav.setBackgroundDrawable(ContextCompat.getDrawable(tbFav.getContext(), R.drawable.ic_me_gusta_boton));

                    }
                }
            });
        }
    } // fin CartaPlayaViewHolder


}
