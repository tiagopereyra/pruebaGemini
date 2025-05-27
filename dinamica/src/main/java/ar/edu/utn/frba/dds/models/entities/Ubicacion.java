package ar.edu.utn.frba.dds.models.entities;

public class Ubicacion {
    private double latitud;
    private double longitud;

    public Ubicacion(double latitud, double longitud) {
        this.latitud = latitud;
        this.longitud = longitud;
    }

    public boolean compararUbicacion(Ubicacion ubicacion) {
        return  ubicacion.latitud==latitud && ubicacion.longitud==longitud;
    }
}
