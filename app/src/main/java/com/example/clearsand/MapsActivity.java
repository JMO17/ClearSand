package com.example.clearsand;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.example.clearsand.javabean.Playa;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {
    private ArrayList<Playa> playas;
    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        //Ponemos la ubicación de la playa
        LatLng ubi = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(ubi).title("Marcador en: "));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(ubi));
    }
}
