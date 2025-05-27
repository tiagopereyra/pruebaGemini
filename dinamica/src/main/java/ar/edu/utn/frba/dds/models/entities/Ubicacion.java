package ar.edu.utn.frba.dds.models.entities;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Ubicacion {
    private double latitud;
    private double longitud;

    public Ubicacion(double latitud, double longitud) {
        this.latitud = latitud;
        this.longitud = longitud;
    }

    public boolean compararUbicacion(Ubicacion ubicacion) {
        return ubicacion != null && ubicacion.latitud == this.latitud && ubicacion.longitud == this.longitud;
    }
}
