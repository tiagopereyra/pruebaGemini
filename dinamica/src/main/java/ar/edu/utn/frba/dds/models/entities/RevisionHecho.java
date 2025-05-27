package ar.edu.utn.frba.dds.models.entities;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;

@Getter
@Setter
public class RevisionHecho {
    private Administrador administrador;
    private LocalDate fechaRevisado;
    private String sugerencia;
    private EstadoRevision estado;

    public RevisionHecho() {
        this.estado = EstadoRevision.PENDIENTE;
    }

    public void aprobar(Administrador admin) {
        this.administrador = admin;
        this.fechaRevisado = LocalDate.now();
        this.estado = EstadoRevision.APROBADO;
        this.sugerencia = null;
    }

    public void aprobarConSugerencias(Administrador admin, String sugerenciaTexto) {
        this.administrador = admin;
        this.fechaRevisado = LocalDate.now();
        this.estado = EstadoRevision.APROBADO_CON_SUGERENCIAS;
        this.sugerencia = sugerenciaTexto;
    }

    public void rechazar(Administrador admin) {
        this.administrador = admin;
        this.fechaRevisado = LocalDate.now();
        this.estado = EstadoRevision.RECHAZADO;
        this.sugerencia = null;
    }
}