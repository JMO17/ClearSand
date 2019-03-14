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
import com.uemdam.clearsand.javabean.Playa;

import java.util.ArrayList;


public class FragmentEvFecha extends Fragment {


    View v;
    private RecyclerView miRecyclerView;
    private ArrayList<Evento> listaEventos;
    private Playa playa= new Playa();


    public FragmentEvFecha() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container, @Nullable Bundle savedInstanceState) {

        v=  inflater.inflate(R.layout.fragment_ev_cerca,container,false);
        miRecyclerView=v.findViewById(R.id.rvCerca);
        AdaptadorEventos adaptadorEventos= new AdaptadorEventos(getContext(),listaEventos, null, 2);
        miRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        miRecyclerView.setAdapter(adaptadorEventos);
        return v;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        listaEventos = new ArrayList<>();

    }


}
