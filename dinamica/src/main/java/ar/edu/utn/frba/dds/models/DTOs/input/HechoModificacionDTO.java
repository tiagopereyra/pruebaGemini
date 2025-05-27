package ar.edu.utn.frba.dds.models.DTOs.input;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;

@Getter
@Setter
public class HechoModificacionDTO {
    private String titulo;
    private String descripcion;
    private String nombreCategoria;
    private LocalDate fechaAcontecimiento;
    private double latitudUbicacion;
    private double longitudUbicacion;
    private String tipoMultimedia;
    private String datosMultimedia;
}