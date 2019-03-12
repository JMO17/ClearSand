package com.uemdam.clearsand;

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

public class EventosTabActivity extends AppCompatActivity{



    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ViewPagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eventos_tab);

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
