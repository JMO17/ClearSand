package com.uemdam.clearsand;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

class ViewPagerAdapter extends FragmentPagerAdapter {

    private final List<Fragment> listaFragmentos= new ArrayList<>();
    private final List<String> listaTitulos= new ArrayList<>();

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return listaFragmentos.get(position);
    }

    @Override
    public int getCount() {
        return listaTitulos.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {

        return listaTitulos.get(position);

    }

    public void addFragments(Fragment fragment, String titulo){

        listaFragmentos.add(fragment);
        listaTitulos.add(titulo);
    }
}
