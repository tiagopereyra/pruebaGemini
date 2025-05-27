package ar.edu.utn.frba.dds.models.DTOs.input;

import ar.edu.utn.frba.dds.models.entities.Fuente;
import ar.edu.utn.frba.dds.models.entities.criterio.Criterio;
import lombok.Data;

import java.util.List;

@Data
public class ColeccionDTO {
    private String ID;
    private List<Criterio> criterios;
    private String titulo;
    private String descripcion;
    //private Fuente fuente;
    // TODO hacer los DTOs de las fuentes
}
