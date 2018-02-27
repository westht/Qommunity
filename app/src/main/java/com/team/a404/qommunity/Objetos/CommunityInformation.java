package com.team.a404.qommunity.Objetos;

/**
 * Created by Sergio Cuadrado on 20/02/2018.
 */

public class CommunityInformation {
    private String nombre;
    private String direccion;

    public CommunityInformation(){

    }
    public CommunityInformation(String nombre, String direccion) {
        this.nombre = nombre;
        this.direccion = direccion;
    }
    public String getNombre() {
        return nombre;
    }

    public String getDireccion() {
        return direccion;
    }


}
