package ar.edu.utn.frba.dds.models.dtos.output;

import lombok.Data;

@Data
public class CategoriaDTO {
    private String nombre;

    public CategoriaDTO(String nombre) {
    }
}
