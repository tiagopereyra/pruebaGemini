package ar.edu.utn.frba.dds.models.DTOs.input;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SolicitudEliminacionCreacionDTO {
    private String idHechoAEliminar;
    private String motivo;
    private String idContribuyenteSolicitante; //Opcional, para saber quién la creó
}