package com.example.clearsand;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.clearsand.javabean.Evento;
import com.example.clearsand.javabean.Playa;
import com.example.clearsand.javabean.Usuario;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class RegisterActivity extends AppCompatActivity {

    private FirebaseAuth fba;
    private FirebaseUser user;

    private DatabaseReference dbR;

    EditText etEmail;
    EditText etPassword;
    EditText etPassword2;
    EditText etNombre;

    String email;
    String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        fba = FirebaseAuth.getInstance();

        etEmail = findViewById(R.id.etEmailRegister);
        etNombre = findViewById(R.id.etNombreRegister);
        etPassword = findViewById(R.id.etContrasenyaRegister);
        etPassword2 = findViewById(R.id.etContrasenyaRepeatRegister);

        dbR = FirebaseDatabase.getInstance().getReference().child("usuarios");
    }

    public void registro(View v) {
        //if (validarDatos()) {
        String warning = validarDatos();
        if (warning == null) {
            fba.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                user = fba.getCurrentUser();
                                Toast.makeText(RegisterActivity.this, getString(R.string.msj_userregistered_register) + ": " + user.getEmail(),
                                        Toast.LENGTH_SHORT).show();

                                //agregando usuario a FIrebase

                                String clave = dbR.push().getKey();

                                ArrayList<Playa> playita = new ArrayList<>();
                                playita.add(new Playa("playa1","tarragona",null));

                                Usuario user = new Usuario(clave,etNombre.getText().toString(),null,etEmail.getText().toString(),null,null,null,null);
                                dbR.child(clave).setValue(user);





                                Intent i = new Intent(RegisterActivity.this, MainActivity.class);
                                //i.putExtra("USER", user.getEmail());
                                startActivity(i);
                            } else {
                                Toast.makeText(RegisterActivity.this, getString(R.string.msj_no_registrado), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

        } else {
            //Toast.makeText(this, getString(R.string.msj_no_data), Toast.LENGTH_LONG).show();
            Toast.makeText(this, warning,
                    Toast.LENGTH_LONG).show();
        }
    }

    private String validarDatos() {

        String msj = null;

        email = etEmail.getText().toString().trim();
        password = etPassword.getText().toString().trim();

        if (email.isEmpty() || password.isEmpty()) {
            msj = "Debe introducirse el email y la password";
        } else if (password.length() < 6) {
            msj = "La password debe contener al menos 6 caracteres";
        }

        return msj;


    }
}
