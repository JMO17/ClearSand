package com.uemdam.clearsand;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.uemdam.clearsand.javabean.Evento;
import com.uemdam.clearsand.javabean.Usuario;

import java.util.ArrayList;

public class TarjetaEventos extends AppCompatActivity {


    //DATABASE
    private DatabaseReference dbR;
    private ChildEventListener cel;

    //COMPONENTES
    private ArrayList<Evento> eventoSel;
    private String id;
    private Usuario[] user;
    String participantes;
    TextView tvNombreEv;
    TextView tvNombrePlaya;
    TextView tvFecha;
    TextView tvDescripcion;
    TextView tvUsuarios;


    private FirebaseAuth fba;
    private FirebaseUser userx;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tarjeta_eventos);

        fba = FirebaseAuth.getInstance();
        userx = fba.getCurrentUser();


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



            }
        });


        //id= Integer.parseInt(getIntent().getStringExtra("ID_EV"));
        id=getIntent().getStringExtra("ID_EV");

        eventoSel= new ArrayList<>();

        dbR= FirebaseDatabase.getInstance().getReference().child("EVENTOS");

        addChildEventListener();

        tvNombreEv=findViewById(R.id.tvNombreEv);
        tvNombrePlaya=findViewById(R.id.tvNombrePlayaEv);
        tvFecha=findViewById(R.id.tvFechaEv);
        tvDescripcion=findViewById(R.id.tvDescripcionEv);
        tvUsuarios=findViewById(R.id.tvUsuariosEv);

    }

    private void addChildEventListener() {
        if (cel==null){
            cel= new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                    Evento ev= dataSnapshot.getValue(Evento.class);
                    if(ev.getIdEventos().equals(id)){
                        eventoSel.add(ev);
                        cargarComponentes();
                    }
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

    public void cargarComponentes(){
        tvNombreEv.setText(eventoSel.get(0).getNombreEvento());
        tvNombrePlaya.setText(eventoSel.get(0).getPlayaEvento().getNombre());
        tvFecha.setText(eventoSel.get(0).getFechaEvento());
        tvDescripcion.setText(eventoSel.get(0).getDescripcionEventos());

        String creador= eventoSel.get(0).getCreadorEvento().getNombreUsuario();

        ArrayList<Usuario> participantesEv= new ArrayList<>();
        participantesEv=eventoSel.get(0).getParticipantesEvento();

         participantes="";
        for(Usuario participante: participantesEv){

             participantes=participantes+"-"+participante.getNombreUsuario()+"\n";
        }

        tvUsuarios.setText("Creador: "+creador+"\n" +participantes);

    }
}
