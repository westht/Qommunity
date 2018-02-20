package com.team.a404.qommunity;

/**
 * Created by Sergio Cuadrado on 01/02/2018.
 */

public class UserInformation {
    public String nombre;
    public String email;
    public String telefono;
    public String urlphoto;


    public UserInformation() {

    }

    public UserInformation(String nombre, String email, String telefono,String urlphoto) {
        this.nombre = nombre;
        this.email = email;
        this.telefono = telefono;
        this.urlphoto = urlphoto;
    }

    public String getPersonName() {
        return nombre;
    }
    public String getTel(){
        return telefono;
    }

    public String getEmail() {
        return email;
    }
    public String getUrlphoto() {
        return urlphoto;
    }
}
