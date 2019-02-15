package com.example.clearsand.javabean;

import java.util.ArrayList;

public class Evento {

    private String nombreEvento;
    private String fechaEvento;
    private Playa playaEvento;
    private Usuario creadorEvento;
    private ArrayList<Usuario> participantesEvento;
    private String fotoUrl="https://firebasestorage.googleapis.com/v0/b/clearsand-4e9e0.appspot.com/o/FotosPlayas%2FTurismo-Alcossebre-Playa-Cargador-Alcossebre.jpg?alt=media&token=40869dd8-171a-4ae2-947d-e6e50dc72a57";
//TODO
    //Hay que poner un atributo Image View Para poder poner una foto en cada evento
    public Evento(){

    }

    public Evento(String nombreEvento, String fechaEvento, Playa playaEvento, Usuario creadorEvento, ArrayList<Usuario> participantesEvento) {
        this.nombreEvento = nombreEvento;
        this.fechaEvento = fechaEvento;
        this.playaEvento = playaEvento;
        this.creadorEvento = creadorEvento;
        this.participantesEvento = participantesEvento;
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

    public String getFotoUrl() {
        return fotoUrl;
    }
}
