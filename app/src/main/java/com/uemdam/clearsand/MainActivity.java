package com.uemdam.clearsand;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.uemdam.clearsand.R;

public abstract class MainActivity extends AppCompatActivity {

    String test;
    String SecondMerge;


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            switch (item.getItemId()) {
                case R.id.nav_eventos:
                    Toast.makeText(MainActivity.this, "Eventos", Toast.LENGTH_LONG).show();
                    return true;
                case R.id.nav_explorar:
                    Toast.makeText(MainActivity.this, "explorar", Toast.LENGTH_LONG).show();
                    return true;
                case R.id.nav_favoritos:
                    Toast.makeText(MainActivity.this, "favoritos", Toast.LENGTH_LONG).show();
                    return true;
                case R.id.nav_perfil:
                    Toast.makeText(MainActivity.this, "perfil", Toast.LENGTH_LONG).show();
                    return true;
            }
            return false;
        }
    };

    public abstract int cargarLayout();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        View v = findViewById(R.id.relative);
        RelativeLayout rel = (RelativeLayout) v;
        getLayoutInflater().inflate(cargarLayout(),rel);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);


    }

}
