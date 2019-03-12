package com.uemdam.clearsand;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;


import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.uemdam.clearsand.javabean.AdaptadorCartaPlaya;
import com.uemdam.clearsand.javabean.Playa;
import com.uemdam.clearsand.javabean.Usuario;

import java.util.ArrayList;

public class MainActivity extends menuAbstractActivity{

    private ArrayList<Playa> datosPlaya;

    /*VISTA*/
    private ProgressBar progBar;

    /*RECYCLED VIEW*/
    private RecyclerView rvCartaPlaya;
    private AdaptadorCartaPlaya adaptador;
    private LinearLayoutManager llManager;

    /*DATABASE*/
    private DatabaseReference dbR;
    private ChildEventListener cel;

    /*USUARIO*/
    private Usuario[] user; //el query devuelve un array de usuarios pero solo utilizamos el primero

    /*LOCATION*/
    private FusedLocationProviderClient flc;
    private Location locUsuario;

    @Override
    public int cargarLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        progBar = findViewById(R.id.progBarMain);

        /*LOCATION*/
        if ( ContextCompat.checkSelfPermission( this, android.Manifest.permission.ACCESS_COARSE_LOCATION ) != PackageManager.PERMISSION_GRANTED ) {

            ActivityCompat.requestPermissions( this, new String[] {  android.Manifest.permission.ACCESS_COARSE_LOCATION  }, 1);
        }

        if ( ContextCompat.checkSelfPermission( this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ) {

            ActivityCompat.requestPermissions( this, new String[] {  Manifest.permission.ACCESS_FINE_LOCATION  }, 2);
        }

        flc = LocationServices.getFusedLocationProviderClient(this);
        try {
            //TODO Pedir permisos
            flc.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    if(location != null) {
                        locUsuario = location;
                    }
                }
            });
        } catch (SecurityException e) {
            Toast.makeText(this, "No se puede acceder a la localización", Toast.LENGTH_SHORT);
        }


        /*--------------------            DATABASE USUARIO                  ----------------------*/
        dbR = FirebaseDatabase.getInstance().getReference().child("usuarios");
        String email = FirebaseAuth.getInstance().getCurrentUser().getEmail();
        Query q = dbR.orderByChild("emailUsuario").equalTo(email);
        user = new Usuario[1];

        q.addListenerForSingleValueEvent(new ValueEventListener() {
            //Cargar datos de usuario
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    user[0] = dataSnapshot1.getValue(Usuario.class);
                    cargarRecycleView();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



    }

    private void cargarRecycleView() {
        /*---------------------                RECYCLE VIEW             --------------------------*/
        rvCartaPlaya = findViewById(R.id.rvListaPlayaMain);
        rvCartaPlaya.setHasFixedSize(true);
        /*LinearLayout*/
        llManager = new LinearLayoutManager(this);
        rvCartaPlaya.setLayoutManager(llManager);

        datosPlaya = new ArrayList<Playa>();
        adaptador = new AdaptadorCartaPlaya(datosPlaya, user, locUsuario);
        adaptador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO borrar el mensaje
                String msg = "Seleccionada la opción " + rvCartaPlaya.getChildAdapterPosition(v) ;
                Toast.makeText(MainActivity.this,msg,Toast.LENGTH_SHORT).show();

//                Intent i = new Intent(MainActivity.this, PerfilPlayaActivity.class);
//                i.putExtra("ID", datosPlaya.get(rvCartaPlaya.getChildAdapterPosition(v)).getId());
//                startActivity(i);
            }
        });
        rvCartaPlaya.setAdapter(adaptador);

        rvCartaPlaya.setItemAnimator(new DefaultItemAnimator());

        /*DATABASE*/
        dbR = FirebaseDatabase.getInstance().getReference().child("PLAYAS");

        addChildEventListener();



    }


    private void addChildEventListener() {
        if(cel == null) {
            cel = new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                    //System.out.println("Nueva playa");
                    progBar.setVisibility(View.VISIBLE);
                    Playa m = dataSnapshot.getValue(Playa.class);
                    datosPlaya.add(m);
                    //System.out.println(m.getNombre());
                    adaptador.notifyItemChanged(datosPlaya.size()-1);
                    adaptador.notifyDataSetChanged();
                    progBar.setVisibility(View.GONE);
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
