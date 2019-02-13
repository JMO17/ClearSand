package com.example.clearsand;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.clearsand.javabeans.AdaptadorCartaPlaya;
import com.example.clearsand.javabeans.Playa;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<Playa> listaTEmporal;

    private RecyclerView rvCartaPlaya;
    private AdaptadorCartaPlaya adaptador;
    private LinearLayoutManager llManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rvCartaPlaya = findViewById(R.id.rvListaPlayaMain);
        rvCartaPlaya.setHasFixedSize(true);

        llManager = new LinearLayoutManager(this);
        rvCartaPlaya.setLayoutManager(llManager);

        cargarListaTemporal();
        adaptador = new AdaptadorCartaPlaya(listaTEmporal);
        rvCartaPlaya.setAdapter(adaptador);
    }

    public void cargarListaTemporal() {
        listaTEmporal = new ArrayList<Playa>();
        listaTEmporal.add(new Playa("Playa 1", 10.0, 10.0));
        listaTEmporal.add(new Playa("Playa 2", 20.0, 20.0));
        listaTEmporal.add(new Playa("Playa 3", 30.0, 30.0));
    }
}
