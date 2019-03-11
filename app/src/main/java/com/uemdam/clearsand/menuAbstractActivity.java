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

public abstract class menuAbstractActivity extends AppCompatActivity {

    String test;
    String SecondMerge;
    int actActual = 1;


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            switch (item.getItemId()) {
                case R.id.nav_explorar:
                    Toast.makeText(menuAbstractActivity.this, "Eventos", Toast.LENGTH_LONG).show();
                    if(actActual != 1) {
                        Intent i1 = new Intent(menuAbstractActivity.this, MainActivity.class);
                        startActivity(i1);
                    }
                    return true;
                case R.id.nav_eventos:
                    Toast.makeText(menuAbstractActivity.this, "explorar", Toast.LENGTH_LONG).show();
                    return true;
                case R.id.nav_favoritos:
                    Toast.makeText(menuAbstractActivity.this, "favoritos", Toast.LENGTH_LONG).show();
                    if(actActual != 3){
                        Intent i3 = new Intent(menuAbstractActivity.this, FavoritosActivity.class);
                        startActivity(i3);
                    }
                    return true;
                case R.id.nav_perfil:
                    Toast.makeText(menuAbstractActivity.this, "perfil", Toast.LENGTH_LONG).show();
                    if(actActual !=4) {
                        Intent i4 = new Intent(menuAbstractActivity.this, UserProfileActivity.class);
                        startActivity(i4);
                    }
                    return true;
            }
            return false;
        }
    };

    public abstract int cargarLayout();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_abstract);

        View v = findViewById(R.id.relative);
        RelativeLayout rel = (RelativeLayout) v;
        getLayoutInflater().inflate(cargarLayout(),rel);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);


    }

    public void setActActual(int actActual) {
        this.actActual = actActual;
    }
}
