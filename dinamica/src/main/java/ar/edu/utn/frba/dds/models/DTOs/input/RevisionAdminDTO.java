package ar.edu.utn.frba.dds.models.DTOs.input;

import ar.edu.utn.frba.dds.models.entities.EstadoRevision;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RevisionAdminDTO {
    private String idHecho;
    private String idAdministrador;
    private EstadoRevision decision;
    private String sugerencias;
}