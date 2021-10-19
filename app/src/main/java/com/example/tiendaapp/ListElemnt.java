package com.example.tiendaapp;
//Clase que se encarga de crear los elementos que se van a utilizar para pasar la fecha y hora.

public class ListElemnt {
    private String fecha;
    private String hora;

    public ListElemnt(String fecha, String hora) {
        this.fecha = fecha;
        this.hora = hora;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

}
