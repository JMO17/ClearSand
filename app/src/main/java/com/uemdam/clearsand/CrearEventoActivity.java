package com.uemdam.clearsand;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

public class CrearEventoActivity extends AppCompatActivity {

    EditText tvTitulo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_eventoo);

        tvTitulo = findViewById(R.id.etTituloEvento);
    }
}
