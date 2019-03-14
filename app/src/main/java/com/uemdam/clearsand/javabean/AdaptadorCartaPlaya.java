package com.uemdam.clearsand.javabean;

import android.content.Context;
import android.content.SharedPreferences;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.uemdam.clearsand.R;

import java.lang.reflect.Array;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Clase Adaptador que recibe los datos de la playa
 * para poder crear las cartas de la playa.
 */
public class AdaptadorCartaPlaya extends RecyclerView.Adapter<AdaptadorCartaPlaya.CartaPlayaViewHolder>
        implements View.OnClickListener, Filterable {

    /*--------------------------------   ATRIBUTOS   ------------------------------------------*/
    public static final String MIS_FAVORITOS = "arhivofav";

    private ArrayList<Playa> datos;
    private ArrayList<Playa> datosFiltrados;
    private View.OnClickListener listener;
    private Usuario[] user;
    private Location locUsuario;

    /*--------------------------------    CONSTRUCTOR  ------------------------------------------*/
    public AdaptadorCartaPlaya(ArrayList<Playa> datos, Usuario[] user, Location locUsuario) {
        this.datos = datos;
        this.user = user;
        this.locUsuario = locUsuario;
        datosFiltrados = datos;
    }

    /*--------------------------------   METODOS ADAPTER  -----------------------------------------*/
    @NonNull
    @Override
    public CartaPlayaViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_playa, viewGroup, false);
        v.setOnClickListener(this);
        CartaPlayaViewHolder cpvh = new CartaPlayaViewHolder(v,viewGroup.getContext(), user, locUsuario);
        return cpvh;
    }

    @Override
    public void onBindViewHolder(@NonNull CartaPlayaViewHolder cartaPlayaViewHolder, int i) {
        cartaPlayaViewHolder.bindPlaya(datosFiltrados.get(i));
    }

    @Override
    public int getItemCount() {
        return datosFiltrados.size();
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


    /*--------------------------------   MERTODO FILTRO -----------------------------------------*/

    /**
     * Quita las tildes de una palabra
     * @param s
     * @return
     */
    public static String quitaDiacriticos(String s) {
        s = Normalizer.normalize(s, Normalizer.Form.NFD);
        s = s.replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");
        return s;
    }

    /**
     * Crea un filtro para el adapter
     * Según el nombre de la playa y la provincia
     * @return
     */
    @Override
    public Filter getFilter() {
        return new Filter() {

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String query = constraint.toString();
                if(query.isEmpty()) {
                    datosFiltrados = datos;
                } else {
                    ArrayList<Playa> filtrados = new ArrayList<>();

                    //BUSQUEDA por NOMBRE
                    for(Playa p: datos) {
                        if(quitaDiacriticos(p.getNombre().toLowerCase()).contains(quitaDiacriticos(query.toLowerCase()))) {
                            filtrados.add(p);
                        }
                    }

                    //BUSQUEDA por PROVINCIA


                    for(Playa p: datos) {
                        if(quitaDiacriticos(p.getComunidad_Autonoma().toLowerCase()).contains(quitaDiacriticos(query.toLowerCase()))) {
                            filtrados.add(p);
                        }
                    }

                    datosFiltrados = filtrados;

                }// fin if-else

                FilterResults filterResults = new FilterResults();
                filterResults.values = datosFiltrados;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                datosFiltrados = (ArrayList<Playa>) results.values;
                notifyDataSetChanged();
            }
        };
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
        private TextView tvComunidad;
        private ToggleButton tbFav;


        private Context contexto;
        /*DATABASE*/
        private DatabaseReference dbR;

        /*USER*/
        private Usuario[] user;
        private ArrayList<String> favoritos;
        private Location locUsuario;

        /**
         * Constructor
         * @param itemView
         */
        public CartaPlayaViewHolder(@NonNull View itemView, Context contexto, Usuario[] user, Location locUsuario) {
            super(itemView);

            ivFoto = itemView.findViewById(R.id.ivFotoCard);
            tvDistancia = itemView.findViewById(R.id.tvDistanciaCard);
            tvNombre = itemView.findViewById(R.id.tvNombreCard);
            tvComunidad = itemView.findViewById(R.id.tvComunidadCard);
            tbFav = itemView.findViewById(R.id.tbFavCard);

            dbR = FirebaseDatabase.getInstance().getReference().child("usuarios");
            this.contexto = contexto;
            this.user = user;
            this.locUsuario = locUsuario;
            favoritos = user[0].getPlayasUsuarioFav();

        }

        /**
         * Recibe los datos de una playa y rellena los views de la carta
         * con esos datos
         * @param playa
         */
        public void bindPlaya(final Playa playa) {
            Glide.with(ivFoto.getContext()).load(playa.getUrlImagen()).into(ivFoto);
            if(locUsuario != null) {
                float distanacia = calcularDistancia(locUsuario, playa);
                tvDistancia.setText(String.format(contexto.getString(R.string.tv_distancia_card), distanacia));
            } else {
                tvDistancia.setText(playa.getCoordenada_geográfica_Latitud() +" | "+ playa.getCoordenada_geográfica_Longitud());
            }

            /*CAMBIAR EL FORMATO DE ECRITURA*/
            tvNombre.setText(playa.getNombre());
            if(playa.getComunidad_Autonoma().toLowerCase().contains("murcia")) {
                tvComunidad.setText("Región de Murcia");
            } else if(playa.getComunidad_Autonoma().toLowerCase().contains("asturias")) {
                tvComunidad.setText("Principado de Asturias");
            }else {
                tvComunidad.setText(playa.getComunidad_Autonoma());

            }

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

                    if(favoritos.contains(playa.getId())){
                        tbFav.setBackgroundDrawable(ContextCompat.getDrawable(tbFav.getContext(), R.drawable.ic_me_gusta_boton));
                        // Quitar de los favoritos de usuario
                        user[0].getPlayasUsuarioFav().remove(playa.getId());
                        dbR.child(user[0].getKeyUsuario()).setValue(user[0]);


                    } else {
                        // Cambiamos el imagen a seleccionado
                        tbFav.setBackgroundDrawable(ContextCompat.getDrawable(tbFav.getContext(), R.drawable.ic_me_gusta_boton_pressed));
                        // Añadir a los favoritos de usuario
                        user[0].getPlayasUsuarioFav().add(playa.getId());
                        dbR.child(user[0].getKeyUsuario()).setValue(user[0]);
                    }
                }
            });
            /*tbFav.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(isChecked) {
                        // Cambiamos el imagen a seleccionado
                        tbFav.setBackgroundDrawable(ContextCompat.getDrawable(tbFav.getContext(), R.drawable.ic_me_gusta_boton_pressed));
                        // Añadir a los favoritos de usuario
                        user[0].getPlayasUsuarioFav().add(playa.getId());
                        dbR.child(user[0].getKeyUsuario()).setValue(user[0]);

                    } else {
                        tbFav.setBackgroundDrawable(ContextCompat.getDrawable(tbFav.getContext(), R.drawable.ic_me_gusta_boton));
                        // Quitar de los favoritos de usuario
                        user[0].getPlayasUsuarioFav().remove(playa.getId());
                        dbR.child(user[0].getKeyUsuario()).setValue(user[0]);
                    }
                }
            });*/
        }

        private float calcularDistancia(Location locUsuario, Playa playa) {
            float resultMetros = 0;

            Location locPlaya = new Location("");
            locPlaya.setLatitude(Double.parseDouble(playa.getCoordenada_Y()));
            locPlaya.setLongitude(Double.parseDouble(playa.getCoordenada_X()));

            resultMetros = locUsuario.distanceTo(locPlaya);

            return  resultMetros/1000; //pasar a kilometros
        }

/*        public void bindPlaya(final Playa playa) {
            Glide.with(ivFoto.getContext()).load(playa.getUrlImagen()).into(ivFoto);
            *//*tvDistancia.setText(String.format(contexto.getString(R.string.tv_distancia_card), playa.getDistancia()));
             *//*
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
                        // mirar si es mejor apply en vez de commit
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
        }*/
    } // fin CartaPlayaViewHolder


}
