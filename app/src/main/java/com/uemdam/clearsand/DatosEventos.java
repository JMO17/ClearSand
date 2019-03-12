package com.uemdam.clearsand;

import com.uemdam.clearsand.javabean.Evento;
import com.uemdam.clearsand.javabean.Playa;

import java.util.ArrayList;

public class DatosEventos {

    private ArrayList<Evento> lista;
    private Playa playa= new Playa("Cocha","Vasco");


    public DatosEventos() {
        lista= new ArrayList<Evento>();
        cargarDatos();
    }

    public void cargarDatos(){

        lista.add(new Evento("Limpiar Playa de la Concha","23-5-2019",playa,R.drawable.ic_ev_cerca,"Este es un evento"));
        lista.add(new Evento("Fiesta ecologista","23-5-2019",playa,R.drawable.ic_ev_cerca,"Este es un evento"));
        lista.add(new Evento("Rescate de animales ","25-7-2019",playa,R.drawable.ic_ev_cerca,"Este es un evento"));
        lista.add(new Evento("Limpiar Playa de Rodeira","26-5-2019",playa,R.drawable.ic_ev_cerca,"Este es un evento"));
        lista.add(new Evento("Limpiar Playa del Arenal","23-5-2019",playa,R.drawable.ic_ev_cerca,"Este es un evento"));
        lista.add(new Evento("Limpiar Playa de la Concha","23-5-2015",playa,R.drawable.ic_ev_cerca,"Este es un evento"));
        lista.add(new Evento("Limpiar Playa de la Concha","23-5-2014",playa,R.drawable.ic_ev_cerca,"Este es un evento"));
        lista.add(new Evento("Limpiar Playa de la Concha","23-5-2013",playa,R.drawable.ic_ev_cerca,"Este es un evento"));

    }


}
