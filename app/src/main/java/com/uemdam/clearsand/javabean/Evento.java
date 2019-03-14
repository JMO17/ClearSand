package com.uemdam.clearsand.javabean;

import java.util.ArrayList;

public class Evento {

    private String nombreEvento;
    private String fechaEvento;
    private Playa playaEvento;
    private Usuario creadorEvento;
    private ArrayList<Usuario> participantesEvento;
    private int imagen;

    private String horaEvento;
    private String idEventos;
    private String descripcionEventos;

    public Evento(){

    }

    public Evento(String nombreEvento, String fechaEvento, Playa playaEvento, Usuario creadorEvento, ArrayList<Usuario> participantesEvento,String horaEvento, String idEventos, String descripcionEventos) {
        this.nombreEvento = nombreEvento;
        this.fechaEvento = fechaEvento;
        this.playaEvento = playaEvento;
        this.creadorEvento = creadorEvento;
        this.participantesEvento = participantesEvento;
        this.horaEvento = horaEvento;
        this.idEventos = idEventos;
        this.descripcionEventos = descripcionEventos;
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

    public String getHoraEvento() {
        return horaEvento;
    }

    public String getIdEventos() {
        return idEventos;
    }

    public String getDescripcionEventos() {
        return descripcionEventos;
    }

    public int getImagen() {
        return imagen;
    }

}
