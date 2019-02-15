package com.example.clearsand.javabean;

import java.util.ArrayList;

public class Evento {

    private String keyEvento;
    private String nombreEvento;
    private String fechaEvento;
    private Playa playaEvento;
    private Usuario creadorEvento;
    private ArrayList<Usuario> participantesEvento;


    public Evento(){

    }

    public Evento(String keyEvento,String nombreEvento, String fechaEvento, Playa playaEvento, Usuario creadorEvento, ArrayList<Usuario> participantesEvento) {
        this.keyEvento = keyEvento;
        this.nombreEvento = nombreEvento;
        this.fechaEvento = fechaEvento;
        this.playaEvento = playaEvento;
        this.creadorEvento = creadorEvento;
        this.participantesEvento = participantesEvento;
    }

    public String getKeyEvento() {
        return keyEvento;
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
}
