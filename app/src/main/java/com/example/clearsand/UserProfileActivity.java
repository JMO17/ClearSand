package com.example.clearsand;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.clearsand.javabean.Usuario;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class UserProfileActivity extends AppCompatActivity {


    private DatabaseReference dbR;
    private FirebaseAuth fa;

    TextView tvNombre;
    TextView tvApellido;

    String email;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        tvNombre = findViewById(R.id.txtNombreUserProfile);
        tvApellido = findViewById(R.id.txtApellidosUserProfile);

        dbR = FirebaseDatabase.getInstance().getReference().child("usuarios");
        fa = FirebaseAuth.getInstance();
        FirebaseUser usuario = fa.getCurrentUser();
        email = usuario.getEmail();

        Toast.makeText(this, email, Toast.LENGTH_LONG).show();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Query qq = dbR.orderByChild("emailUsuario").equalTo("Jamiro@hola.nrt");


                qq.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        Usuario user = dataSnapshot.getValue(Usuario.class);

                        //TODO PORQUE MIERDA NO ME FUNCIONA ESTO -- PREGUNTAR A PILAR
                        String nom = user.getNombreUsuario();
                        Toast.makeText(UserProfileActivity.this, nom, Toast.LENGTH_LONG).show();

                        tvNombre.setText(String.format(getString(R.string.txt_t_NombreUserProfile), user.getNombreUsuario()));
                        tvApellido.setText(String.format(getString(R.string.txt_ApellidosUserProfile), user.getEmailUsuario()));


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();


            }
        });


    }


}

