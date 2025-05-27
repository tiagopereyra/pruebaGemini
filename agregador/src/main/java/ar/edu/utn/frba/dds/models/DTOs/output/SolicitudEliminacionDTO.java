package ar.edu.utn.frba.dds.models.DTOs.output;

import ar.edu.utn.frba.dds.models.entities.EstadoSolicitud;
import lombok.Data;

import java.time.LocalDate;

@Data
public class SolicitudEliminacionDTO {
    private String motivo;
    private EstadoSolicitud estado;
    private LocalDate fechaCarga;
    private LocalDate fechaRevision;
    private HechoDTO hecho;
    private AdministradorDTO administradorEvaluador;
}
