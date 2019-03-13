package com.uemdam.clearsand.javabean;

import java.util.ArrayList;

public class Evento {

    private String nombreEvento;
    private String fechaEvento;
    private Playa playaEvento;
    private Usuario creadorEvento;
    private ArrayList<Usuario> participantesEvento;
    private int imagen;
    private String descripcion;


    public Evento() {
    }

    public Evento(String nombreEvento, String fechaEvento, Playa playaEvento, Usuario creadorEvento, ArrayList<Usuario> participantesEvento, String descripcion) {
        this.nombreEvento = nombreEvento;
        this.fechaEvento = fechaEvento;
        this.playaEvento = playaEvento;
        this.creadorEvento = creadorEvento;
        this.participantesEvento = participantesEvento;
        this.descripcion=descripcion;
    }



    public Evento(String nombreEvento, String fechaEvento, Playa playaEvento, int imagen, String descripcion) {
        this.nombreEvento = nombreEvento;
        this.fechaEvento = fechaEvento;
        this.playaEvento = playaEvento;
        this.imagen = imagen;
        this.descripcion = descripcion;
    }

    public String getNombreEvento() {
        return nombreEvento;
    }

    public String getFechaEvento() {
        return fechaEvento;
    }

    public Playa getPlayaEvento() {
        return playaEvento;
    }

    public Usuario getCreadorEvento() {
        return creadorEvento;
    }

    public ArrayList<Usuario> getParticipantesEvento() {
        return participantesEvento;
    }

    public int getImagen() {
        return imagen;
    }

    public String getDescripcion() {
        return descripcion;
    }
}
