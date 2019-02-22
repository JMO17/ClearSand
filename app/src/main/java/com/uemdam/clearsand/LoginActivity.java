package com.uemdam.clearsand;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 *
 */
public class LoginActivity extends AppCompatActivity {

    public static final int RC_REG = 1;

    /* AUTENTIFICACIÓN*/
    private FirebaseAuth fba;
    private FirebaseUser user;
    private EditText etEmail;
    private EditText etPassword;
    private String email;
    private String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // If the Android version is lower than Jellybean, use this call to hide
        // the status bar.
        if (Build.VERSION.SDK_INT < 16) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        } else {
            View decorView = getWindow().getDecorView();
            // Hide the status bar.
            int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
            decorView.setSystemUiVisibility(uiOptions);
            // Remember that you should never show the action bar if the
            // status bar is hidden, so hide that too if necessary.
            /*Esconder la AppBar*/
            getSupportActionBar().hide();
        }



        /* Inicialización de atributos*/
        fba = FirebaseAuth.getInstance();

        etEmail = findViewById(R.id.etEmailLogin);
        etPassword = findViewById(R.id.etPasswordLogin);
    }

    /**
     * Comprueba y realiza una pequeña validación de los campos
     * email (no vacío) y password (no vacío y longitud mínima de 6 caracteres)
     *
     * @return null si hay datos válidos, o un mensaje con el error correspondiente
     */
    private String validarDatos() {
        String msj = null;

        email = etEmail.getText().toString().trim();
        password = etPassword.getText().toString().trim();

        if (email.isEmpty() && password.isEmpty()) {
            msj = getString(R.string.msj_no_data_login);

        } else if (email.isEmpty()) {
            msj = getString(R.string.msj_no_email_login);

        }else if (password.isEmpty()) {
            msj = getString(R.string.msj_no_password_login);

        } else if(password.length() < 6) {
            msj = getString(R.string.msj_pwd_length_login);

        }

        return msj;
    }

    /**
     * Comprueba si los datos son válidos. Mostrándo un toast
     * si se ha producido un error de validación.
     * Si los datos son válidos, comprueba si el email y password son correctos
     * iniciando la siguiente actividad o mostrando un mensaje si son incorrectos
     *
     * @param v View
     */
    public void acceder(View v) {
        //if (validarDatos()) {
        String warning = validarDatos();

        if (warning == null) {
            fba.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                user = fba.getCurrentUser();
                                Toast.makeText(LoginActivity.this,
                                        getString(R.string.msj_logado_login) + user.getEmail(),
                                        Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(LoginActivity.this, UserProfileActivity.class);
                                //i.putExtra("USER", user.getEmail());
                                startActivity(i);
                            } else {
                                Toast.makeText(LoginActivity.this,
                                        getString(R.string.msj_no_accede_login),
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    }); // fin fba.signInWithEmailAndPassword()

        } else {
            //TODO centrar con gravedad?
            Toast.makeText(this, warning, Toast.LENGTH_LONG).show();
        }

    } // fin acceder

    public void registrar(View v) {
        Intent i = new Intent(this, RegisterActivity.class);
        startActivityForResult(i, RC_REG);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_REG) {
            //TODO rellenar los campos de email y password
        }
    }
}
