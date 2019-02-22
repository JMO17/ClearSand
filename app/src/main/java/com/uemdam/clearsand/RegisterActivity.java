package com.uemdam.clearsand;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.uemdam.clearsand.javabean.Usuario;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.BufferedInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class RegisterActivity extends AppCompatActivity {
//hl
    private FirebaseAuth fba;
    private FirebaseUser user;

    private DatabaseReference dbR;

    EditText etEmail;
    EditText etPassword;
    EditText etPassword2;
    EditText etNombre;

    private String email;
    private String password;
    private String password2;

    final private int RC_PHOTO_ADJ = 1;

    private Uri selectedImage;
    private Boolean imagenSubida = false;


    Boolean pas = false;

    //Todo final private int RC_PHOTO_CAM = 2;


    // STORAGE
    private FirebaseStorage mFirebaseStorage;
    private StorageReference mFotoStorageRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        //extraemos el drawable en un bitmap
        Drawable originalDrawable = getResources().getDrawable(R.drawable.perfilpreview);
        Bitmap originalBitmap = ((BitmapDrawable) originalDrawable).getBitmap();

        //creamos el drawable redondeado
        RoundedBitmapDrawable roundedDrawable =
                RoundedBitmapDrawableFactory.create(getResources(), originalBitmap);

        //asignamos el CornerRadius
        roundedDrawable.setCornerRadius(originalBitmap.getHeight());

        ImageView imageView = (ImageView) findViewById(R.id.imageView);

        imageView.setImageDrawable(roundedDrawable);


        fba = FirebaseAuth.getInstance();

        etEmail = findViewById(R.id.etEmailRegister);
        etNombre = findViewById(R.id.etNombreRegister);
        etPassword = findViewById(R.id.etContrasenyaRegister);
        etPassword2 = findViewById(R.id.etContrasenyaRepeatRegister);

        dbR = FirebaseDatabase.getInstance().getReference().child(getString(R.string.clave_User));

        // STORAGE
        mFirebaseStorage = FirebaseStorage.getInstance();
        mFotoStorageRef = mFirebaseStorage.getReference().child(getString(R.string.clave_UserPhoto));
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

                                /**
                                 * agregando usuario a Firebase
                                 */


                                /**
                                 * Primero voy a subir la imagen si se ha agregado correctamente.
                                 */
                                final String clave = dbR.push().getKey();
                                if (imagenSubida) { //TODO NO FUNCIONA ME CAGO EN DIOS
                                    Uri selectedUri = selectedImage;

                                    StorageReference fotoRef = mFotoStorageRef.child(selectedUri.getLastPathSegment());
                                    UploadTask ut = fotoRef.putFile(selectedUri);

                                    Handler handler = new Handler();
                                    handler.postDelayed(null,5000);

                                    ut.addOnSuccessListener(RegisterActivity.this, new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                        @Override
                                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                            Task<Uri> task = taskSnapshot.getStorage().getDownloadUrl();
                                            task.addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                @Override
                                                public void onSuccess(Uri uri) {
                                                    Usuario user = new Usuario(clave, etNombre.getText().toString(), null, etEmail.getText().toString().toLowerCase(), null, uri.toString(), null, null);
                                                    dbR.child(clave).setValue(user);
                                                }
                                            });
                                        }
                                    });
                                } else {

                                    Usuario user = new Usuario(clave, etNombre.getText().toString(), null, etEmail.getText().toString().toLowerCase(), null, null, null, null);
                                    dbR.child(clave).setValue(user);
                                }

                                //TODO RESTAURAR BUENO ------ Intent i = new Intent(RegisterActivity.this, MainActivity.class);
                                System.out.print(pas.toString());
                                Intent i = new Intent(RegisterActivity.this, UserProfileActivity.class);


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
        password2 = etPassword2.getText().toString().trim();

        if (email.isEmpty() || password.isEmpty()) {
            msj = "Debe introducirse el email y la password";
        } else if (password.length() < 6) {
            msj = "La password debe contener al menos 6 caracteres";
        } else if (!password.equals(password2)) {
            msj = "Las contraseñas deben de coincidir";
        }

        return msj;
    }

    public void enviarFoto(View v) {
        /*abrirá un selector de archivos para ayudarnos a elegir entre cualquier imagen JPEG almacenada localmente en el dispositivo */
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/jpeg");
        intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
        startActivityForResult(Intent.createChooser(intent,
                "Complete la acción usando"), RC_PHOTO_ADJ);

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_PHOTO_ADJ) {
            if (resultCode == RESULT_OK) {


                selectedImage = data.getData();


                InputStream is;

                try {
                    is = getContentResolver().openInputStream(selectedImage);
                    BufferedInputStream bis = new BufferedInputStream(is);
                    Bitmap bitmap = BitmapFactory.decodeStream(bis);

                    //creamos el drawable redondeado
                    RoundedBitmapDrawable roundedDrawable =
                            RoundedBitmapDrawableFactory.create(getResources(), bitmap);

                    //asignamos el CornerRadius
                    roundedDrawable.setCornerRadius(bitmap.getHeight());


                    ImageView iv = (ImageView) findViewById(R.id.imageView);
                    //TODO iv.setImageBitmap(bitmap);
                    iv.setImageDrawable(roundedDrawable);

                    imagenSubida = true;


                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }


            } else {
                Toast.makeText(this, "Error al obtener la foto", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }
}
