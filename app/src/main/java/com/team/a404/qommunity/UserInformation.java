package com.team.a404.qommunity;

/**
 * Created by Sergio Cuadrado on 01/02/2018.
 */

public class UserInformation {
    public String nombre;
    public String email;
    public String tel;

    public UserInformation() {
    }


    public UserInformation(String nombre, String email, String tel) {
        this.nombre = nombre;
        this.email = email;
        this.tel = tel;
    }

    public String getPersonName() {
        return nombre;
    }
    public String getTel(){
        return tel;
    }

    public String getEmail() {
        return email;
    }
}
