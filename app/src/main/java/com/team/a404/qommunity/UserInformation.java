package com.team.a404.qommunity;

/**
 * Created by Sergio Cuadrado on 01/02/2018.
 */

public class UserInformation {
    public String nombre;
    public String email;
    public String telefono;


    public UserInformation(String nombre, String email, String telefono) {
        this.nombre = nombre;
        this.email = email;
        this.telefono = telefono;
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
}
