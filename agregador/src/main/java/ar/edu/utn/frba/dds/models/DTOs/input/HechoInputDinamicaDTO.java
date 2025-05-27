package ar.edu.utn.frba.dds.models.DTOs.input;

import ar.edu.utn.frba.dds.models.DTOs.output.AdministradorDTO;
import lombok.Data;

import java.time.LocalDate;

@Data
public class HechoInputDinamicaDTO {
    private String titulo;
    private String descripcion;
    private CategoriaDTO categoria;
    private LocalDate fechaAcontecimiento;
    private UbicacionDTO ubicacion;
    private AdministradorDTO administradorDTO;

}
