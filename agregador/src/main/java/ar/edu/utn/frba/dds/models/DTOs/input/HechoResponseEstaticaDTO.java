package ar.edu.utn.frba.dds.models.DTOs.input;

import lombok.Data;

import java.util.List;

@Data
public class HechoResponseEstaticaDTO {
    private List<HechoInputEstaticaDTO> hechos;
}
