package com.team.a404.qommunity.Objetos;

/**
 * Created by Pestar on 01/03/2018.
 */

public class favoresInformation {

    private String descripcion;
    private String fecha;
    private String hora;
    private String usuario;
    private String estado;


    private String usuarioacepta;

    public String getDescripcion() {
        return descripcion;
    }

    public String getFecha() {
        return fecha;
    }

    public String getHora() {
        return hora;
    }

    public String getUsuario() {
        return usuario;
    }

    public String getEstado() {
        return estado;
    }
    public String getUsuario_acepta() {
        return usuarioacepta;
    }


    public favoresInformation() {
    }

    public favoresInformation(String descripcion, String fecha, String hora, String usuario, String estado,String usuarioacepta) {
        this.descripcion = descripcion;
        this.fecha = fecha;
        this.hora = hora;
        this.usuario = usuario;
        this.estado = estado;
        this.usuarioacepta = usuarioacepta;
    }

}
