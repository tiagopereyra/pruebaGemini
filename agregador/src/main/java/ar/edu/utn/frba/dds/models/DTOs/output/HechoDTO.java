package ar.edu.utn.frba.dds.models.DTOs.output;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class HechoDTO {
    private Long id;
    private String titulo;
    private String descripcion;
    private LocalDate fecha;
    private UbicacionDTO ubicacion;
    private CategoriaDTO categoria;
    private TipoDeFuenteDTO tipoDeFuente;
    private List<EtiquetaDTO> etiquetas;
    private List<MultimediaDTO> archivosMultimedia;
    private List<RevisionHechoDTO> revisiones;
}
