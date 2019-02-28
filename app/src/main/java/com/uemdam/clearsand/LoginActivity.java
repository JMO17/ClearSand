package com.uemdam.clearsand;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

/**
 *
 */
public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    public static final int RC_REG = 1;

    private static final String TAG = "GoogleActivity";
    private static final int RC_SIGN_IN = 9001;


    private GoogleSignInClient mGoogleSignInClient;

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

        // Configure sign-in to request the user's ID, email address, and basic
// profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        findViewById(R.id.sign_in_button).setOnClickListener(this);
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

        } else if (password.isEmpty()) {
            msj = getString(R.string.msj_no_password_login);

        } else if (password.length() < 6) {
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

        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e);
                // [START_EXCLUDE]
                updateUI(null);
                // [END_EXCLUDE]
            }
        }

    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = fba.getCurrentUser();
        updateUI(currentUser);
    }

    private void updateUI(FirebaseUser user) {
        //hideProgressDialog();
        if (user != null) {
           /** mStatusTextView.setText(getString(R.string.google_status_fmt, user.getEmail()));
            mDetailTextView.setText(getString(R.string.firebase_status_fmt, user.getUid()));

            findViewById(R.id.signInButton).setVisibility(View.GONE);
            findViewById(R.id.signOutAndDisconnect).setVisibility(View.VISIBLE);*/

            Toast.makeText(LoginActivity.this,"usuario conectado"+user.getEmail() +" - "+ user.getUid(), Toast.LENGTH_LONG).show();
        } else {
           /** mStatusTextView.setText(R.string.signed_out);
            mDetailTextView.setText(null);

            findViewById(R.id.signInButton).setVisibility(View.VISIBLE);
            findViewById(R.id.signOutAndDisconnect).setVisibility(View.GONE);*/
           Toast.makeText(LoginActivity.this,"usuario no conectado", Toast.LENGTH_LONG).show();
        }
    }


    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        fba.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = fba.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Snackbar.make(findViewById(R.id.main_layout), "Authentication Failed.", Snackbar.LENGTH_SHORT).show();
                            updateUI(null);
                        }

                        // ...
                    }
                });
    }


    public void signInGoogle(View view) {
        signIn();

    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }


    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.sign_in_button) {
            signIn();
        }
    }
}
