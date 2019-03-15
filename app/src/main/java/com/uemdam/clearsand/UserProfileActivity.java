package com.uemdam.clearsand;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.uemdam.clearsand.javabean.Usuario;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class UserProfileActivity extends menuAbstractActivity {


    private DatabaseReference dbR;
    private FirebaseAuth fa;
    private FirebaseUser usuario;

    private TextView tvNombre;
    private TextView tvApellido;
    private TextView tvEdad;
    private TextView tvEmail;
    private ImageView tvImagen;

    /*EDITABLE*/
    private TextView txtNombreEdit;
    private TextView txtApellidoEdit;
    private TextView txtEdadEdit;
    private EditText etNombre;
    private EditText etApellidos;
    private EditText etEdad;

    private Button btnAplicar;

    String email;

    private final Usuario[] user = new Usuario[1];

    /*BOOLEAN EDIR CHECK*/

    private Boolean editQQ = false;

    FirebaseAuth fba;

    @Override
    public int cargarLayout() {
        return R.layout.activity_user_profile;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setContentView(R.layout.activity_user_profile);
        setActActual(USUARIO);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");

        tvNombre = findViewById(R.id.txtNombreUserProfile);
        tvApellido = findViewById(R.id.txtApellidosUserProfile);
        tvEdad = findViewById(R.id.txtEdadUserProfile);
        tvEmail = findViewById(R.id.txtEmailUserProfile);
        tvImagen = findViewById(R.id.imagenDelPerfil);

        /*EDITABLE*/

        etApellidos = findViewById(R.id.etApellidosProfile);
        etNombre = findViewById(R.id.etNombreProfile);
        etEdad = findViewById(R.id.etEdadProfile);
        btnAplicar = findViewById(R.id.btnAplicarProfile);

        txtNombreEdit = findViewById(R.id.txtNombreEditProfile);
        txtApellidoEdit = findViewById(R.id.txtApellidoEditProfile2);
        txtEdadEdit = findViewById(R.id.txtEdadEditProfile3);


        setVisibilityEdit(false);


        dbR = FirebaseDatabase.getInstance().getReference().child("usuarios");
        fa = FirebaseAuth.getInstance();
        usuario = fa.getCurrentUser();
        email = usuario.getEmail();


        Toast.makeText(this, email, Toast.LENGTH_LONG).show();


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        FloatingActionButton fabExit = findViewById(R.id.fabExit);
         fba = FirebaseAuth.getInstance();
        fabExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fba.signOut();
                Intent loginIntent = new Intent(UserProfileActivity.this,LoginActivity.class);
                loginIntent.putExtra("001","exit");
                startActivity(loginIntent);

                finish();
            }
        });


        cargarDatos();


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                if(!editQQ){
                    setVisibilityEdit(true);
                    Usuario userx = user[0];
                    chargueData(userx);
                    editQQ = true;
                }


                Snackbar.make(view, "Edita la información", Snackbar.LENGTH_LONG)
                        .setAction("Cancelar", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                setVisibilityEdit(false);
                                cargarDatos();
                                editQQ = false;
                            }
                        }).show();


            }
        });


    }

    private void chargueData(@NonNull Usuario userx) {

        if(userx.getNombreUsuario()!=null){
            etNombre.setHint(userx.getNombreUsuario());
        }


        if(userx.getApellidosUsuario()!=null){
            etApellidos.setHint(userx.getApellidosUsuario());
       }

       if(userx.getEdadUsuario()!=null){
            etEdad.setHint(userx.getEdadUsuario());
        }
    }

    private void setVisibilityEdit(boolean b) {

        if (b) {
            tvNombre.setVisibility(View.GONE);
            tvApellido.setVisibility(View.GONE);
            tvEdad.setVisibility(View.GONE);
            tvEmail.setVisibility(View.GONE);

            etApellidos.setVisibility(View.VISIBLE);
            etNombre.setVisibility(View.VISIBLE);
            etEdad.setVisibility(View.VISIBLE);
            btnAplicar.setVisibility(View.VISIBLE);
            txtNombreEdit.setVisibility(View.VISIBLE);
            txtApellidoEdit.setVisibility(View.VISIBLE);
            txtEdadEdit.setVisibility(View.VISIBLE);

        } else {
            tvNombre.setVisibility(View.VISIBLE);
            tvApellido.setVisibility(View.VISIBLE);
            tvEdad.setVisibility(View.VISIBLE);
            tvEmail.setVisibility(View.VISIBLE);

            etApellidos.setVisibility(View.GONE);
            etNombre.setVisibility(View.GONE);
            etEdad.setVisibility(View.GONE);
            btnAplicar.setVisibility(View.GONE);
            txtNombreEdit.setVisibility(View.GONE);
            txtApellidoEdit.setVisibility(View.GONE);
            txtEdadEdit.setVisibility(View.GONE);
        }


    }

    private void cargarDatos() {
        Query qq = dbR.orderByChild("emailUsuario").equalTo(email);

        qq.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    user[0] = dataSnapshot1.getValue(Usuario.class);
                }


                String nom;

                if (user[0].getNombreUsuario() != null) {
                    nom = user[0].getNombreUsuario();
                    getSupportActionBar().setTitle(nom);
                } else {
                    nom = getString(R.string.txt_SinEspecificar);
                }

                String urlImagen;

                if (user[0].getFotoUsuario() != null) {
                    Glide.with(tvImagen.getContext())
                            .load(user[0].getFotoUsuario())
                            .into(tvImagen);
                } else {

                }


                String edad;
                if (user[0].getEdadUsuario() != null) {
                    edad = user[0].getEdadUsuario();
                } else {
                    edad = getString(R.string.txt_SinEspecificar);
                }

                String apellido;
                if (user[0].getApellidosUsuario() != null) {
                    apellido = user[0].getApellidosUsuario();
                } else {
                    apellido = getString(R.string.txt_SinEspecificar);
                }

                String email;
                if (user[0].getEmailUsuario() != null) {
                    email = user[0].getEmailUsuario();
                } else {
                    email = getString(R.string.txt_SinEspecificar);
                }

                Toast.makeText(UserProfileActivity.this, nom, Toast.LENGTH_LONG).show();

                tvNombre.setText(String.format(getString(R.string.txt_t_NombreUserProfile), user[0].getNombreUsuario()));
                tvApellido.setText(String.format(getString(R.string.txt_ApellidosUserProfile), apellido));
                tvEmail.setText(String.format(getString(R.string.txt_EmailUserProfile), email));
                tvEdad.setText(String.format(getString(R.string.txt_EdadUserProfile), edad));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }


    public void guardarCambios(View view) {



        if(etApellidos.getText().toString().trim().equalsIgnoreCase("")){
            mensaje("No has agregado ningun Apellido");

        }else{
            user[0].setApellidosUsuario(etApellidos.getText().toString().trim());
        }

        if(etEdad.getText().toString().trim().equalsIgnoreCase("")){
            mensaje("No has agregado Edad");
        }else {
            user[0].setEdadUsuario(etEdad.getText().toString().trim());
        }

        if(etNombre.getText().toString().trim().equalsIgnoreCase("")){
            mensaje("El campo nombre no puede estar vacio");
            etNombre.setText("");

        }else{
            user[0].setNombreUsuario(etNombre.getText().toString().trim());

            dbR.child(user[0].getKeyUsuario()).setValue(user[0]);

            cargarDatos();

            setVisibilityEdit(false);
            editQQ=false;
        }

    }

    private void mensaje(String s) {
        Toast.makeText(this,s,Toast.LENGTH_LONG).show();
    }
}

