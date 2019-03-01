package com.uemdam.clearsand;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.uemdam.clearsand.javabean.Evento;

import java.util.ArrayList;

public class FragmentEvCerca extends Fragment{

     RecyclerView recyclerViewEvCerca;
    private LinearLayoutManager miLayoutManager;
    private AdaptadorEventos adaptador;
    private ArrayList<Evento> lista;

    public FragmentEvCerca() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,  ViewGroup container, @Nullable Bundle savedInstanceState) {

          View v=  inflater.inflate(R.layout.fragment_ev_cerca,container,false);

        lista= (new DatosEventos().getLista());
        recyclerViewEvCerca=v.findViewById(R.id.rvCerca);

        recyclerViewEvCerca.setHasFixedSize(true);
        recyclerViewEvCerca.setLayoutManager(new LinearLayoutManager(getContext()));

        adaptador= new AdaptadorEventos(lista);

        return v;
    }
}
