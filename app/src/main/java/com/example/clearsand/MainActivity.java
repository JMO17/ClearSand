package com.example.clearsand;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.clearsand.javabeans.AdaptadorCartaPlaya;
import com.example.clearsand.javabeans.Playa;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<Playa> datosPlaya;

    /*RECYCLED VIEW*/
    private RecyclerView rvCartaPlaya;
    private AdaptadorCartaPlaya adaptador;
    private LinearLayoutManager llManager;

    /*DATABASE*/
    private DatabaseReference dbR;
    private ChildEventListener cel;

    /*STORAGE*/
    private FirebaseStorage mFirebaseStorage;
    private StorageReference mFotoStorageRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        /*RECYCLE VIEW*/
        rvCartaPlaya = findViewById(R.id.rvListaPlayaMain);
        rvCartaPlaya.setHasFixedSize(true);
        rvCartaPlaya.setItemAnimator(new DefaultItemAnimator());
            /*LinearLayout*/
        llManager = new LinearLayoutManager(this);
        rvCartaPlaya.setLayoutManager(llManager);

        datosPlaya = new ArrayList<Playa>();
        adaptador = new AdaptadorCartaPlaya(datosPlaya);
        rvCartaPlaya.setAdapter(adaptador);

        cargarListaTemporal();

        /*DATABASE*/

        /*STORAGE*/
        mFirebaseStorage = FirebaseStorage.getInstance();
        mFotoStorageRef = mFirebaseStorage.getReference().child("Fotos"); //Nos posicionamos en la carpeta Fotos
    }

    public void cargarListaTemporal() {
        datosPlaya.add(new Playa("Playa 1", 10.0, 10.0));
        datosPlaya.add(new Playa("Playa 2", 20.0, 20.0));
        datosPlaya.add(new Playa("Playa 3", 30.0, 30.0));
    }
}
