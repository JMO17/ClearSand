package com.uemdam.clearsand;

import android.content.Context;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.uemdam.clearsand.javabean.Evento;
import com.uemdam.clearsand.javabean.Playa;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class  AdaptadorEventos extends RecyclerView.Adapter<AdaptadorEventos.MyViewHolder> implements View.OnClickListener{

    Context context;
    ArrayList<Evento> lista;
    View.OnClickListener listener;
    Location locUsuario;


    public AdaptadorEventos(Context context, ArrayList<Evento> lista, final Location locUsuario, int origen) {

        this.context = context;
        this.lista = lista;
        this.locUsuario=locUsuario;

        if(origen==1){
            if(locUsuario==null){


            }else{
                Collections.sort(lista, new Comparator<Evento>() {
                    @Override
                    public int compare(Evento ev1, Evento ev2) {

                        if(calcularDistancia(locUsuario,ev1.getPlayaEvento())>calcularDistancia(locUsuario,ev1.getPlayaEvento())){

                            return 1;

                        }else if(calcularDistancia(locUsuario,ev1.getPlayaEvento())<=calcularDistancia(locUsuario,ev1.getPlayaEvento())){

                            return -1;
                        }else{
                            return 0;
                        }

                    }
                });

            }

        }else{

            Collections.sort(lista, new Comparator<Evento>() {
                @Override
                public int compare(Evento ev1, Evento ev2) {

                    String fecha1=ev1.getFechaEvento();
                    String fecha2= ev2.getFechaEvento();
                    //Date date1=new SimpleDateFormat("dd/MM/yyyy").parse(sDate1);
                    try {
                        Date date1= new SimpleDateFormat("dd/MM/yyyy").parse(fecha1);
                        Date date2= new SimpleDateFormat("dd/MM/yyyy").parse(fecha2);
                        if(date1.compareTo(date2)>0){

                            return 1;
                        }else if(date1.compareTo(date2)<=0){

                            return -1;
                        }else{

                            return 0;
                        }

                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    return 0;
                }
            });

        }

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v;
        v=LayoutInflater.from(context).inflate(R.layout.rv_eventos,parent,false);
        v.setOnClickListener(this);
        MyViewHolder vh= new MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.tvNomEv.setText(lista.get(position).getNombreEvento());
        holder.tvNomPlaya.setText(lista.get(position).getPlayaEvento().getNombre());
        holder.tvFecha.setText(lista.get(position).getFechaEvento());
        holder.ivEv.setImageResource(lista.get(position).getImagen());

        if(locUsuario != null) {
            float distanacia = calcularDistancia(locUsuario, lista.get(position).getPlayaEvento());
            holder.tvDistancia.setText(String.format(context.getString(R.string.tv_distancia_card), distanacia));
        } else {
            holder.tvDistancia.setText(lista.get(position).getPlayaEvento().getCoordenada_geográfica_Latitud() +" | "+ lista.get(position).getPlayaEvento().getCoordenada_geográfica_Longitud());
        }
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

    public static  class MyViewHolder extends RecyclerView.ViewHolder{

        private TextView tvNomEv;
        private TextView tvDistancia;
        private TextView tvNomPlaya;
        private TextView tvFecha;
        private ImageView ivEv;



        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            tvNomEv= itemView.findViewById(R.id.tvNomEv);
            tvNomPlaya=itemView.findViewById(R.id.tvNomPlaya);
            tvDistancia=itemView.findViewById(R.id.tvDistancia);
            tvFecha=itemView.findViewById(R.id.tvFecha);
            ivEv=itemView.findViewById(R.id.ivPlayaEv);
        }
    }

    private float calcularDistancia(Location locUsuario, Playa playa) {
        float resultMetros = 0;

        Location locPlaya = new Location("");
        locPlaya.setLatitude(Double.parseDouble(playa.getCoordenada_Y()));
        locPlaya.setLongitude(Double.parseDouble(playa.getCoordenada_X()));

        resultMetros = locUsuario.distanceTo(locPlaya);

        return  resultMetros/1000; //pasar a kilometros
    }





}
