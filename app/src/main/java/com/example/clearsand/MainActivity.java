package com.example.clearsand;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.clearsand.javabeans.AdaptadorCartaPlaya;
import com.example.clearsand.javabeans.Playa;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
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

        /*DATABASE*/
        //dbR = FirebaseDatabase.getInstance().getReference().child("PLAYA");
        dbR = FirebaseDatabase.getInstance().getReference().child("playita");

        addChildEventListener();

        /*RECYCLE VIEW*/
        rvCartaPlaya = findViewById(R.id.rvListaPlayaMain);
        rvCartaPlaya.setHasFixedSize(true);
            /*LinearLayout*/
        llManager = new LinearLayoutManager(this);
        rvCartaPlaya.setLayoutManager(llManager);

        datosPlaya = new ArrayList<Playa>();
        adaptador = new AdaptadorCartaPlaya(datosPlaya);
        rvCartaPlaya.setAdapter(adaptador);

        rvCartaPlaya.setItemAnimator(new DefaultItemAnimator());




        datosPlaya.add(new Playa("Playa I", "MALLORCA", null));

        /*STORAGE*/
        mFirebaseStorage = FirebaseStorage.getInstance();
        mFotoStorageRef = mFirebaseStorage.getReference().child("Fotos"); //Nos posicionamos en la carpeta Fotos
    }


    private void addChildEventListener() {
        if(cel == null) {
            cel = new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                    System.out.println("Nuevo playa");
                    Playa m = dataSnapshot.getValue(Playa.class);
                    //Playa m2 = new Playa(dataSnapshot.getValue())
                    datosPlaya.add(m);
                    adaptador.notifyItemChanged(datosPlaya.size()-1);
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
