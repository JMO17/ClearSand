package com.example.clearsand.javabeans;

public class Playa {

    private String nombre;
    private double coordX;
    private double coordY;

    public Playa(String nombre, double coordX, double coordY) {
        this.nombre = nombre;
        this.coordX = coordX;
        this.coordY = coordY;
    }

    public String getNombre() {
        return nombre;
    }
    public double getDistancia() {
        //TODO calcular la distancia
        return coordX+coordY;
    }
}
