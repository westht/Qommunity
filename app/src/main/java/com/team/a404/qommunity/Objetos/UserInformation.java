package com.team.a404.qommunity.Objetos;

/**
 * Created by Sergio Cuadrado on 01/02/2018.
 */

public class UserInformation {
    public String nombre;
    public String email;
    public String telefono;
    public String urlfoto;
    public int ncomunidades;


    public UserInformation() {
    }

    public UserInformation(String nombre, String email, String telefono,String urlfoto,int ncomunidades) {
        this.nombre = nombre;
        this.email = email;
        this.telefono = telefono;
        this.urlfoto = urlfoto;
        this.ncomunidades=ncomunidades;
    }

    public String getPersonName() {return nombre;}
    public String getTel(){
        return telefono;
    }

    public String getEmail() {
        return email;
    }
    public String getUrlphoto() {
        return urlfoto;
    }
    public int getNcomunidades() {
        return ncomunidades;
    }
}
