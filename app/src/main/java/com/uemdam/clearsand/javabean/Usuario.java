package com.uemdam.clearsand.javabean;

import java.util.ArrayList;

public class Usuario {

    private String keyUsuario;
    private String nombreUsuario;
    private String apellidosUsuario;
    private String emailUsuario;
    private String edadUsuario;
    private String fotoUsuario;
    private ArrayList<String> playasUsuarioFav;
    private ArrayList<Evento> eventosUsuario;

    public Usuario() {

    }


    public Usuario(String keyUsuario, String nombreUsuario, String apellidosUsuario, String emailUsuario, String edadUsuario, String fotoUsuario, ArrayList<String> playasUsuarioFav, ArrayList<Evento> eventosUsuario) {
        this.nombreUsuario = nombreUsuario;
        this.apellidosUsuario = apellidosUsuario;
        this.emailUsuario = emailUsuario;
        this.edadUsuario = edadUsuario;
        this.fotoUsuario = fotoUsuario;
        this.playasUsuarioFav = playasUsuarioFav;
        this.eventosUsuario = eventosUsuario;

        this.keyUsuario = keyUsuario;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public String getApellidosUsuario() {
        return apellidosUsuario;
    }

    public String getEmailUsuario() {
        return emailUsuario;
    }

    public String getEdadUsuario() {
        return edadUsuario;
    }

    public String getFotoUsuario() {
        return fotoUsuario;
    }

    public ArrayList<String> getPlayasUsuarioFav() {
        return playasUsuarioFav;
    }

    public ArrayList<Evento> getEventosUsuario() {
        return eventosUsuario;
    }

    public void setKeyUsuario(String keyUsuario) {
        this.keyUsuario = keyUsuario;
    }

    public String getKeyUsuario() {
        return keyUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public void setApellidosUsuario(String apellidosUsuario) {
        this.apellidosUsuario = apellidosUsuario;
    }

    public void setEdadUsuario(String edadUsuario) {
        this.edadUsuario = edadUsuario;
    }
}
