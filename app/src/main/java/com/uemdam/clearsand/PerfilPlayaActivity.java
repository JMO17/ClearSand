package com.uemdam.clearsand;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.uemdam.clearsand.javabean.Playa;

import java.util.ArrayList;

public class PerfilPlayaActivity extends AppCompatActivity implements OnMapReadyCallback {

    //Mapa
    private GoogleMap mMap;
    BitmapDescriptor icon;

    //DATABASE
    private DatabaseReference dbR;
    private ChildEventListener cel;

    //Componentes
    private ArrayList<Playa> datoPlaya;
    private int id;
    private double latitud;
    private double longitud;
    private String nombre;
    TextView tvNombre;
    ImageView ivPlaya;
    TextView tvEstado;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_playa2);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        getSupportActionBar().hide();

        id = Integer.parseInt(getIntent().getStringExtra("ID"));
        datoPlaya = new ArrayList<>();
        //DATABASE
        dbR = FirebaseDatabase.getInstance().getReference().child("PLAYAS");

        icon = generateBitmapDescriptor(this, R.drawable.ic_swimming_zone_marker);


        addChildEventListener();

        tvNombre = findViewById(R.id.tvNombrePlayaPerfilActivity);
        ivPlaya = findViewById(R.id.ivPerfilPLayaActivity);
        tvEstado = findViewById(R.id.tvEstadoPlaya);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

            mMap = googleMap;
            mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);//Imágenes satelite

            mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
            mMap.getUiSettings().setZoomControlsEnabled(true);

    }

    private void cargarMapa(BitmapDescriptor icon) {
        latitud = Double.parseDouble(datoPlaya.get(0).getCoordenada_Y());
        longitud = Double.parseDouble(datoPlaya.get(0).getCoordenada_X());
        LatLng mad = new LatLng(latitud, longitud);

        mMap.addMarker(new MarkerOptions().position(mad).title(datoPlaya.get(0).getNombre()).icon(icon));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(mad));

        CameraPosition camPos = new CameraPosition.Builder()
                .target(new LatLng(latitud, longitud))
                .zoom(7)
                .build();
        CameraUpdate camUpd3 = CameraUpdateFactory.newCameraPosition(camPos);
        mMap.animateCamera(camUpd3);
    }

    public void crearEvento(View v){
        Intent e = new Intent(this, CrearEventoActivity.class);
        e.putExtra("IDEVENTOS",id);
        startActivity(e);
    }

    public void comoLlegar(View v){
        Intent i = new Intent(this,MapsActivity.class);
        i.putExtra("IDMAPA",id);
        i.putExtra("LATITUD",latitud);
        i.putExtra("LONGITUD",longitud);
        i.putExtra("NOMBRE",datoPlaya.get(0).getNombre());
        startActivity(i);
    }

    public static BitmapDescriptor generateBitmapDescriptor(Context context, int resId) {
        Drawable drawable = ContextCompat.getDrawable(context, resId);
        drawable.setBounds(
                0,
                0,
                drawable.getIntrinsicWidth(),
                drawable.getIntrinsicHeight());
        Bitmap bitmap = Bitmap.createBitmap(
                drawable.getIntrinsicWidth(),
                drawable.getIntrinsicHeight(),
                Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }

    private void addChildEventListener() {
        if (cel == null) {
            cel = new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                    Playa m = dataSnapshot.getValue(Playa.class);
                    if(Integer.parseInt(m.getId())== id) {
                        datoPlaya.add(m);
                        cargarComponentes();
                        cargarMapa(icon);
                    }

                }

                @Override
                public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                }

                @Override
                public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

                }

                @Override
                public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            };

            dbR.addChildEventListener(cel);
        }
    }

    public void cargarComponentes(){
        nombre = datoPlaya.get(0).getNombre();
        Glide.with(ivPlaya.getContext()).load(datoPlaya.get(0).getUrlImagen()).into(ivPlaya);
        tvNombre.setText(nombre);
        tvEstado.setText(String.format(getString(R.string.estado_playa),datoPlaya.get(0).getEstado()));
    }
}
