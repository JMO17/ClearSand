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
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.uemdam.clearsand.javabean.Evento;
import com.uemdam.clearsand.javabean.Usuario;

import java.util.ArrayList;

public class TarjetaEventos extends AppCompatActivity {


    //DATABASE
    private DatabaseReference dbR;
    private ChildEventListener cel;

    //COMPONENTES
    private ArrayList<Evento> eventoSel;
    Evento ev;
    private String id;
    String participantes="";
    TextView tvNombreEv;
    TextView tvNombrePlaya;
    TextView tvFecha;
    TextView tvDescripcion;
    TextView tvCreador;
    TextView tvParticipantes;


    //USUARIO
    private Usuario[] user;
    String nombreUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tarjeta_eventos);


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
                    nombreUsuario = user[0].getNombreUsuario();

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



        id=getIntent().getStringExtra("ID_EV");

        eventoSel= new ArrayList<>();

        dbR= FirebaseDatabase.getInstance().getReference().child("EVENTOS");

        addChildEventListener();


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String anotacion=nombreUsuario;

                addChildEventListener();
                if(participantes.contains(anotacion)){

                    String participantesAuxiliar=anotacion+"";
                    participantes=participantes.replaceAll(participantesAuxiliar,"");

                    participantesAuxiliar=""+anotacion;
                    participantes.replaceAll(participantesAuxiliar,"");

                    tvParticipantes.setText("Participantes: \n"+participantes.trim());

                    //user[0].getEventosUsuario().remove(ev.getIdEventos());
                    //dbR.child(user[0].getKeyUsuario()).setValue(user[0]);


                }else{

                    participantes= participantes.trim()+"\n"+nombreUsuario;

                    tvParticipantes.setText("Participantes: \n"+participantes.trim());

                  //  user[0].getEventosUsuario().add(ev.getIdEventos());
                  //  dbR.child(user[0].getKeyUsuario()).setValue(user[0]);

                }


            }
        });

        tvNombreEv=findViewById(R.id.tvNombreEv);
        tvNombrePlaya=findViewById(R.id.tvNombrePlayaEv);
        tvFecha=findViewById(R.id.tvFechaEv);
        tvDescripcion=findViewById(R.id.tvDescripcionEv);
        tvCreador=findViewById(R.id.tvCreador);
        tvParticipantes= findViewById(R.id.tvParticipantes);

    }

    private void addChildEventListener() {
        if (cel==null){
            cel= new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                     ev= dataSnapshot.getValue(Evento.class);
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
        tvNombrePlaya.setText("Playa: "+eventoSel.get(0).getPlayaEvento().getNombre());
        tvFecha.setText("Fecha: "+eventoSel.get(0).getFechaEvento());
        tvDescripcion.setText("Descripción del evento: "+eventoSel.get(0).getDescripcionEventos());
        tvCreador.setText("Creador: "+eventoSel.get(0).getCreadorEvento().getNombreUsuario());
       cargarParticipantes();

    }

    public  void cargarParticipantes(){



        ArrayList<Usuario> participantesEv= new ArrayList<>();
        participantesEv=eventoSel.get(0).getParticipantesEvento();


        for(Usuario participante: participantesEv){

            participantes=participantes+""+participante.getNombreUsuario()+"\n".trim();
        }

        tvParticipantes.setText("Participantes: "+"\n" +participantes.trim());
    }
}
