package ar.edu.utn.frba.dds.models.entities;

import ar.edu.utn.frba.dds.models.entities.hecho.Hecho;

import java.time.LocalDate;

import static ar.edu.utn.frba.dds.models.entities.EstadoSolicitud.*;

public class SolicitudEliminacion {
    private String motivo;
    private EstadoSolicitud estado;
    private LocalDate fechaCarga;
    private LocalDate fechaRevision;
    private Hecho hecho;
    private Administrador administradorEvaluador;

    public SolicitudEliminacion(String motivo, Hecho hecho) {
        this.motivo = motivo;
        this.estado = PENDIENTE;
        this.fechaCarga = LocalDate.now();
        this.hecho = hecho;
    }

    public void setEstado(EstadoSolicitud estado) {
        this.estado = estado;
    }

    public EstadoSolicitud getEstado() {
        return estado;
    }


    private void revisarSolicitud(Administrador administrador) {
        this.fechaRevision = LocalDate.now();
        this.administradorEvaluador = administrador;
    }

    public void aceptarSolicitud(Administrador administrador) {
        this.estado = EstadoSolicitud.ACEPTADA;
        hecho.marcarEliminado();
        revisarSolicitud(administrador);
    }

    public void rechazarSolicitud(Administrador administrador) {
        this.estado = EstadoSolicitud.RECHAZADA;
        revisarSolicitud(administrador);
    }


    public void setFechaCarga(LocalDate fechaCarga) {
        this.fechaCarga = fechaCarga;
    }

    public void setFechaRevision(LocalDate fechaRevision) {
        this.fechaRevision = fechaRevision;
    }
}
