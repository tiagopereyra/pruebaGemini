package ar.edu.utn.frba.dds.models.DTOs.output;

import ar.edu.utn.frba.dds.models.entities.EstadoRevision;
import ar.edu.utn.frba.dds.models.entities.TipoDeFuente;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;

@Getter
@Setter
public class HechoDinamicoDTO {
    private String identificador;
    private String titulo;
    private String descripcion;
    private String nombreCategoria;
    private LocalDate fechaAcontecimiento;
    private LocalDate fechaCarga;
    private UbicacionOutputDTO ubicacion;
    private String tipoMultimedia;
    private String referenciaMultimedia;
    private String nombreContribuyente;
    private EstadoRevision estadoRevision;
    private TipoDeFuente origen;
    private boolean eliminado;
}
