package ar.edu.utn.frba.dds.models.DTOs.output;

import lombok.Data;

@Data
public class RevisionHechoDTO {
    private Long id;
    private String comentario;
    private String autor;
    private String estado;
}
