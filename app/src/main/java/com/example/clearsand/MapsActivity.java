package com.example.clearsand;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;

import com.example.clearsand.javabean.Playa;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {
    private ArrayList<Playa> datosPlayas;
    private GoogleMap mMap;

    /*DATABASE*/
    private DatabaseReference dbR;
    private ChildEventListener cel;

    private int id;
    private  Playa playa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        datosPlayas = new ArrayList<>();

        /*DATABASE*/
        dbR = FirebaseDatabase.getInstance().getReference().child("PLAYAS");

        addChildEventListener();

        id = getIntent().getIntExtra("IDMAPA", 0);
        playa = null;

        for (Playa com : datosPlayas) {
            if (Integer.parseInt(com.getId()) == id) {
                playa = com;
            }
        }

    }

    private void addChildEventListener() {
        if (cel == null) {
            cel = new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                    Playa m = dataSnapshot.getValue(Playa.class);
                    datosPlayas.add(m);

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
        BitmapDescriptor icon = generateBitmapDescriptor(this, R.drawable.ic_swimming_zone_marker);


        if (playa == null) {
            for (Playa plas : datosPlayas) {
                LatLng gms = new LatLng(Double.parseDouble(plas.getCoordenada_X()), Double.parseDouble(plas.getCoordenada_Y()));

                mMap.addMarker(new MarkerOptions().position(gms).title(plas.getNombre())).setIcon(icon);
            }
            LatLng gmsAux = new LatLng(Double.parseDouble(datosPlayas.get(0).getCoordenada_X()), Double.parseDouble(datosPlayas.get(0).getCoordenada_Y()));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(gmsAux));
            mMap.moveCamera(CameraUpdateFactory.zoomTo(9));
        } else {

            LatLng localizacionPlaya = new LatLng(Double.parseDouble(playa.getCoordenada_X()), Double.parseDouble(playa.getCoordenada_Y()));
            mMap.addMarker(new MarkerOptions().position(localizacionPlaya).title(playa.getNombre())).setIcon(icon);

            mMap.moveCamera(CameraUpdateFactory.newLatLng(localizacionPlaya));
            mMap.moveCamera(CameraUpdateFactory.zoomTo(10));

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