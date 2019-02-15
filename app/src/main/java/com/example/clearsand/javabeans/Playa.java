package com.example.clearsand.javabeans;

import java.util.ArrayList;

public class Playa {

    private String nombrePlaya;
    private String ubicacionPLaya;
    private ArrayList<String> fotosUrlPlaya;

    public Playa() {

    }

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

 /*   private String Nombre;
    private String Coordenada_X;
    private String Coordenada_Y;

    public Playa(String nombre, String Coordenada_X, String Coordenada_Y) {
        this.Nombre = nombre;
        this.Coordenada_X = Coordenada_X;
        this.Coordenada_Y = Coordenada_Y;
    }

    public String getNombre() {
        return Nombre;
    }
    public double getDistancia() {
        //TODO calcular la distancia
        return (Double.parseDouble(Coordenada_X)  + Double.parseDouble(Coordenada_Y));
    }*/
}
