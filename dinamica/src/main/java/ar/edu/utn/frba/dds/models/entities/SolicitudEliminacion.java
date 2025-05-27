package ar.edu.utn.frba.dds.models.entities;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
public class SolicitudEliminacion {
    private String id;
    private String motivo;
    private EstadoSolicitud estado;
    private LocalDate fechaCarga;
    private LocalDate fechaRevision;
    private Hecho hecho;
    private Administrador administradorEvaluador;

    public SolicitudEliminacion(String motivo, Hecho hecho) {
        this.id = UUID.randomUUID().toString();
        this.motivo = motivo;
        this.hecho = hecho;
        this.estado = EstadoSolicitud.PENDIENTE;
        this.fechaCarga = LocalDate.now();
        if (hecho != null) {
            hecho.agregarSolicitud(this);
        }
    }

    private void registrarRevision(Administrador administrador) {
        this.fechaRevision = LocalDate.now();
        this.administradorEvaluador = administrador;
    }

    public void aceptarSolicitud(Administrador administrador) {
        this.estado = EstadoSolicitud.ACEPTADA;
        if (this.hecho != null) {
            this.hecho.marcarEliminado();
        }
        registrarRevision(administrador);
    }

    public void rechazarSolicitud(Administrador administrador) {
        this.estado = EstadoSolicitud.RECHAZADA;
        registrarRevision(administrador);
    }

    public void rechazarPorSpam(Administrador administradorSistema) {
        this.estado = EstadoSolicitud.RECHAZADA_SPAM;
        registrarRevision(administradorSistema);
    }
}