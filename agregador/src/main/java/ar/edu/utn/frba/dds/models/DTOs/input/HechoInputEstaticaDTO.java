package ar.edu.utn.frba.dds.models.DTOs.input;

import lombok.Data;

import java.time.LocalDate;

@Data
public class HechoInputEstaticaDTO {
    private String titulo;
    private String descripcion;
    private CategoriaDTO categoria;
    private LocalDate fechaAcontecimiento;
    private UbicacionDTO ubicacion;
}

