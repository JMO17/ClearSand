package com.example.clearsand;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class PerfilPlayaActivity extends AppCompatActivity implements OnMapReadyCallback {

    //Mapa
    private GoogleMap mMap;

    //DATABASE
    private DatabaseReference dbR;
    private ChildEventListener cel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_playa);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        getSupportActionBar().hide();

        //DATABASE
        dbR = FirebaseDatabase.getInstance().getReference().child("PLAYAS");

        //addChildEventListener();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);//Imágenes satelite

        mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        mMap.getUiSettings().setZoomControlsEnabled(true);

        // Add a marker in Madrid and move the camera
        LatLng mad = new LatLng(40, -3);
        mMap.addMarker(new MarkerOptions().position(mad).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(mad));

        CameraPosition camPos = new CameraPosition.Builder()
                .target(new LatLng(40, -4))
                .zoom(5)
                .build();
        CameraUpdate camUpd3 = CameraUpdateFactory.newCameraPosition(camPos);
        mMap.animateCamera(camUpd3);



    }

    public void crearEvento(){

    }

}
