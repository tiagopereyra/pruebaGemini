package ar.edu.utn.frba.dds.models.dtos.output;

import lombok.Data;

@Data
public class UbicacionDTO {
    private double latitud;
    private double longitud;

    public void setLatitud(double latitud) {
    }

    public void setLongitud(double longitud) {
    }
}
