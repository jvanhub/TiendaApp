package com.example.tiendaapp;
//Clase que se encarga de crear los elementos que se van a utilizar para pasar la fecha y hora.

public class ListElemnt {
    private String fecha;
    private String hora;
    private String idCita;

    public ListElemnt(String fecha, String hora, String idCita) {
        this.fecha = fecha;
        this.hora = hora;
        this.idCita = idCita;
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

    public String getIdCita() {
        return idCita;
    }

    public void setIdCita(String idCita) {
        this.idCita = idCita;
    }
}
