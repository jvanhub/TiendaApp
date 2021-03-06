package com.example.tiendaapp;

/**
 * Clase que se encarga de crear los elementos que se van a utilizar para pasar la fecha y hora.
 */
public class ListElemnt {
    private String servicio;
    private String fecha;
    private String hora;
    private String idCita;

    /**
     * Método constructor.
     */
    public ListElemnt(String servicio, String fecha, String hora, String idCita) {
        this.servicio = servicio;
        this.fecha = fecha;
        this.hora = hora;
        this.idCita = idCita;
    }

    /**
     * Métodos setters y getters.
     */

    public String getServicio() {
        return servicio;
    }

    public void setServicio(String servicio) {
        this.servicio = servicio;
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
