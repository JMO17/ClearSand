package com.uemdam.clearsand;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationProvider;
import android.os.Bundle;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;


import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.uemdam.clearsand.javabean.Evento;


import java.util.ArrayList;



public class FragmentEvCerca extends Fragment{


    View v;
    AdaptadorEventos adaptadorEventos;

    private RecyclerView miRecyclerView;
    private ArrayList<Evento> listaEventos;

    //DATABASE
    private DatabaseReference dbR;
    private ChildEventListener cel;


    private Location locUsuario;
    private FusedLocationProviderClient flc;




    public FragmentEvCerca() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container, @Nullable Bundle savedInstanceState) {

        addChildEventListener();

        flc = LocationServices.getFusedLocationProviderClient(getActivity());
        try {

            flc.getLastLocation().addOnSuccessListener(getActivity(), new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    if(location != null) {
                        locUsuario = location;
                        adaptadorEventos.setLocUsuario(locUsuario);
                        adaptadorEventos.notifyDataSetChanged();
                    }
                }
            });
        } catch (SecurityException e) {
            Toast.makeText(getActivity(), "No se puede acceder a la localización", Toast.LENGTH_SHORT).show();
        }



           v=  inflater.inflate(R.layout.fragment_ev_cerca,container,false);
           miRecyclerView=v.findViewById(R.id.rvCerca);
            adaptadorEventos= new AdaptadorEventos(getContext(),listaEventos, locUsuario, 1);
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

        dbR = FirebaseDatabase.getInstance().getReference().child("EVENTOS");


        listaEventos = new ArrayList<>();


    }






    public void addChildEventListener(){
        if (cel==null){
            cel= new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                    Evento ev= dataSnapshot.getValue(Evento.class);


                    listaEventos.add(ev);
                    adaptadorEventos.organizarLista();


                    adaptadorEventos.notifyItemChanged(listaEventos.size()-1);
                    adaptadorEventos.notifyDataSetChanged();

                }

                @Override
                public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                }

                @Override
                public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

                }

                @Override
                public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            };
            dbR.addChildEventListener(cel);
        }



    }
}
