package com.uemdam.clearsand;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.uemdam.clearsand.javabean.Evento;
import com.uemdam.clearsand.javabean.Playa;
import com.uemdam.clearsand.javabean.Usuario;

import java.util.ArrayList;

public class EventosTabActivity extends menuAbstractActivity{



    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ViewPagerAdapter adapter;





    //Datos de prueba
    Playa playa = new Playa("Playa De prueba");
    Usuario userCreador= new Usuario("Jacinto");
    ArrayList<Usuario> usuarios= new ArrayList<>();



    @Override
    public int cargarLayout() {
        return R.layout.activity_eventos_tab;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_eventos_tab);
        setActActual(EVENTOS);
        getSupportActionBar().hide();

        //Datos Prueba
        usuarios.add(new Usuario("pepe"));



        tabLayout=findViewById(R.id.tablayout);
        viewPager=findViewById(R.id.viewpager);
        adapter= new ViewPagerAdapter(getSupportFragmentManager());

        adapter.addFragments(new FragmentEvCerca(),"");
        adapter.addFragments(new FragmentEvFecha(),"");
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);


        tabLayout.getTabAt(0).setIcon(R.drawable.ic_ev_cerca);
        tabLayout.getTabAt(1).setIcon(R.drawable.ic_ev_fecha);


    }



}
