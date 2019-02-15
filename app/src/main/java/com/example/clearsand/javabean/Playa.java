package com.example.clearsand.javabean;

import java.util.ArrayList;

public class Playa {

    private String nombrePlaya;
    private String ubicacionPLaya;
    private ArrayList<String> fotosUrlPlaya;

   // public Playa() {

   // }

    public Playa(String nombrePlaya, String ubicacionPLaya, ArrayList<String> fotosUrlPlaya) {
        this.nombrePlaya = nombrePlaya;
        this.ubicacionPLaya = ubicacionPLaya;
        this.fotosUrlPlaya = fotosUrlPlaya;
    }

    public String getNombrePlaya() {
        return nombrePlaya;
    }

    public String getUbicacionPLaya() {
        return ubicacionPLaya;
    }

    public ArrayList<String> getFotosUrlPlaya() {
        return fotosUrlPlaya;
    }
}
