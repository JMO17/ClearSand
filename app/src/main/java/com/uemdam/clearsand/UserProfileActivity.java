package com.example.clearsand;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
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
    FirebaseUser usuario;

    TextView tvNombre;
    TextView tvApellido;
    TextView tvEdad;
    TextView tvEmail;
    ImageView tvImagen;

    String email;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");

        tvNombre = findViewById(R.id.txtNombreUserProfile);
        tvApellido = findViewById(R.id.txtApellidosUserProfile);
        tvEdad = findViewById(R.id.txtEdadUserProfile);
        tvEmail= findViewById(R.id.txtEmailUserProfile);
        tvImagen = findViewById(R.id.imagenDelPerfil);

        dbR = FirebaseDatabase.getInstance().getReference().child("usuarios");
        fa = FirebaseAuth.getInstance();
        usuario = fa.getCurrentUser();
        email = usuario.getEmail();


        Toast.makeText(this, email, Toast.LENGTH_LONG).show();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Query qq = dbR.orderByChild("emailUsuario").equalTo(email);
                final Usuario[] user = new Usuario[1];

                qq.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                        for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                            user[0] = dataSnapshot1.getValue(Usuario.class);
                        }


                        //Usuario user = dataSnapshot.getValue(Usuario.class);
                        //  user = dataSnapshot.child("usuarios").getValue(Usuario.class);
                        //TODO PORQUE MIERDA NO ME FUNCIONA ESTO -- PREGUNTAR A PILAR
                        //TODO OKAY YA FUNCIONA PERO LO HAGO CON EL FOR EACH DE ARRIBA, LA CONSULTA RETORNA UN DATASNAPSOT QUE LUEGO RECORRO POR  CADA UNO DE SUS HIJOS;
                        String nom ;

                        if(user[0].getNombreUsuario()!=null){
                            nom = user[0].getNombreUsuario();
                            getSupportActionBar().setTitle(nom+" wakanda");
                        }else{
                            nom = getString(R.string.txt_SinEspecificar);
                        }

                        String urlImagen;

                        if(user[0].getFotoUsuario()!=null){
                            Glide.with(tvImagen.getContext())
                                    .load(user[0].getFotoUsuario())
                                    .into(tvImagen);
                        }else{

                        }


                        String edad;
                        if(user[0].getEdadUsuario()!=null){
                             edad =user[0].getEdadUsuario() ;
                        }else{
                            edad  = getString(R.string.txt_SinEspecificar);
                        }

                        String apellido;
                        if(user[0].getApellidosUsuario()!=null){
                            apellido = user[0].getApellidosUsuario();
                        }else{
                            apellido = getString(R.string.txt_SinEspecificar);
                        }

                        String email;
                        if(user[0].getEmailUsuario()!=null){
                            email = user[0].getEmailUsuario();
                        }else{
                            email = getString(R.string.txt_SinEspecificar);
                        }

                        Toast.makeText(UserProfileActivity.this, nom, Toast.LENGTH_LONG).show();

                        tvNombre.setText(String.format(getString(R.string.txt_t_NombreUserProfile), user[0].getNombreUsuario()));
                        tvApellido.setText(String.format(getString(R.string.txt_ApellidosUserProfile), apellido));
                        tvEmail.setText(String.format(getString(R.string.txt_EmailUserProfile),email));
                        tvEdad.setText(String.format(getString(R.string.txt_EdadUserProfile),edad));

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

