package ar.edu.utn.frba.dds.models.DTOs.output;

import lombok.Data;

@Data
public class UbicacionDTO {
    private Long id;
    private String nombre;
    private String coordenadas;
}
