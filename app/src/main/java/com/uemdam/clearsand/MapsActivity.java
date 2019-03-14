package com.uemdam.clearsand;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;

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

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {
    private ArrayList<Playa> datosPlayas;
    private GoogleMap mMap;

    /*DATABASE*/
    private DatabaseReference dbR;
    private ChildEventListener cel;

    private int id;
    private double latitud;
    private double longitud;
    private String nombre;
    private  Playa playa;

    BitmapDescriptor icon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps2);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        datosPlayas = new ArrayList<>();

        /*DATABASE*/
        dbR = FirebaseDatabase.getInstance().getReference().child("PLAYAS");

        icon = generateBitmapDescriptor(this, R.drawable.ic_swimming_zone_marker);

        addChildEventListener();

        id = getIntent().getIntExtra("IDMAPA", 0);
        latitud = getIntent().getDoubleExtra("LATITUD", 0.0);
        longitud = getIntent().getDoubleExtra("LONGITUD", 0.0);
        nombre = getIntent().getStringExtra("NOMBRE");
        playa = null;

    }

    private void addChildEventListener() {
        if (cel == null) {
            cel = new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                    Playa m = dataSnapshot.getValue(Playa.class);
                    datosPlayas.add(m);
                    if (id == 0) {
                        LatLng gms = new LatLng(Double.parseDouble(m.getCoordenada_Y()), Double.parseDouble(m.getCoordenada_X()));

                        mMap.addMarker(new MarkerOptions().position(gms).title(m.getNombre())).setIcon(icon);
                        LatLng gmsAux = new LatLng(40.4167, -3.70325);
                        mMap.moveCamera(CameraUpdateFactory.newLatLng(gmsAux));
                        mMap.moveCamera(CameraUpdateFactory.zoomTo(4));
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

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        //BitmapDescriptor icon = generateBitmapDescriptor(this, R.drawable.ic_swimming_zone_marker);


        if (id == 0) {
          /*  for (Playa plas : datosPlayas) {
                LatLng gms = new LatLng(Double.parseDouble(plas.getCoordenada_X()), Double.parseDouble(plas.getCoordenada_Y()));

                mMap.addMarker(new MarkerOptions().position(gms).title(plas.getNombre())).setIcon(icon);
            }*/
            LatLng gmsAux = new LatLng(40.4167, -3.70325);
            mMap.moveCamera(CameraUpdateFactory.newLatLng(gmsAux));
            mMap.moveCamera(CameraUpdateFactory.zoomTo(6));
        } else {

            /*LatLng localizacionPlaya = new LatLng(Double.parseDouble(playa.getCoordenada_X()), Double.parseDouble(playa.getCoordenada_Y()));
            mMap.addMarker(new MarkerOptions().position(localizacionPlaya).title(playa.getNombre())).setIcon(icon);

            mMap.moveCamera(CameraUpdateFactory.newLatLng(localizacionPlaya));
            mMap.moveCamera(CameraUpdateFactory.zoomTo(10));*/
            LatLng mad = new LatLng(latitud, longitud);

            mMap.addMarker(new MarkerOptions().position(mad).title(nombre).icon(icon));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(mad));

            CameraPosition camPos = new CameraPosition.Builder()
                    .target(new LatLng(latitud, longitud))
                    .zoom(14)
                    .build();
            CameraUpdate camUpd3 = CameraUpdateFactory.newCameraPosition(camPos);
            mMap.animateCamera(camUpd3);
        }


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
}
