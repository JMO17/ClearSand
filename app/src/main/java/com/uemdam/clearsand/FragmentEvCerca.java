package com.uemdam.clearsand;

import android.content.Intent;
import android.os.Bundle;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.uemdam.clearsand.javabean.Evento;
import com.uemdam.clearsand.javabean.Playa;

import java.util.ArrayList;
import java.util.List;


public class FragmentEvCerca extends Fragment{


    View v;
    AdaptadorEventos adaptadorEventos;

    private RecyclerView miRecyclerView;
    private ArrayList<Evento> listaEventos;
    private Playa playa= new Playa();
    EventosTabActivity eta;




    public FragmentEvCerca() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container, @Nullable Bundle savedInstanceState) {

           v=  inflater.inflate(R.layout.fragment_ev_cerca,container,false);
           miRecyclerView=v.findViewById(R.id.rvCerca);
            adaptadorEventos= new AdaptadorEventos(getContext(),listaEventos);
           miRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
           miRecyclerView.setAdapter(adaptadorEventos);

           adaptadorEventos.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   Intent i= new Intent(getActivity(), TarjetaEventos.class);
                   i.putExtra("NOM_EV",listaEventos.get(miRecyclerView.indexOfChild(v)).getNombreEvento());
                   startActivityForResult(i, 1);
               }
           });
           return v;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        listaEventos = new ArrayList<>();

        cargarDatos();
    }



    public void cargarDatos(){

       listaEventos=eta.listaEventos;
    }
}
