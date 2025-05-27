package ar.edu.utn.frba.dds.models.entities;

import java.time.LocalDate;
import java.util.UUID; // Asegúrate de importar UUID

public class SolicitudEliminacion {
    private String id; // CAMBIO IMPORTANTE: Añadir campo ID
    private String motivo;
    private EstadoSolicitud estado;
    private LocalDate fechaCarga;
    private LocalDate fechaRevision;
    private Hecho hecho;
    private Administrador administradorEvaluador;

    public SolicitudEliminacion(String motivo, Hecho hecho) {
        this.id = UUID.randomUUID().toString(); // CAMBIO IMPORTANTE: Asignar ID único
        this.motivo = motivo;
        this.hecho = hecho;
        this.estado = EstadoSolicitud.PENDIENTE; // Correcto
        this.fechaCarga = LocalDate.now();
    }

    // --- GETTERS ---
    public String getId() { // CAMBIO IMPORTANTE: Añadir getter para ID
        return id;
    }

    public String getMotivo() {
        return motivo;
    }

    public EstadoSolicitud getEstado() {
        return estado;
    }

    public LocalDate getFechaCarga() {
        return fechaCarga;
    }

    public LocalDate getFechaRevision() {
        return fechaRevision;
    }

    public Hecho getHecho() {
        return hecho;
    }

    public Administrador getAdministradorEvaluador() {
        return administradorEvaluador;
    }

    // --- SETTERS (si son necesarios externamente) ---
    public void setEstado(EstadoSolicitud estado) {
        this.estado = estado;
    }

    // No es común un setter para fechaCarga si se establece en el constructor.
    // public void setFechaCarga(LocalDate fechaCarga) {
    // this.fechaCarga = fechaCarga;
    // }

    // La fecha de revisión y el administrador evaluador se establecen internamente
    // al aceptar o rechazar.

    // --- MÉTODOS DE ACCIÓN ---
    private void registrarRevision(Administrador administrador) {
        this.fechaRevision = LocalDate.now();
        this.administradorEvaluador = administrador;
    }

    public void aceptarSolicitud(Administrador administrador) {
        this.estado = EstadoSolicitud.ACEPTADA;
        if (this.hecho != null) { // Buena práctica verificar nulidad
            this.hecho.marcarEliminado();
        }
        registrarRevision(administrador);
    }

    public void rechazarSolicitud(Administrador administrador) {
        this.estado = EstadoSolicitud.RECHAZADA;
        registrarRevision(administrador);
    }

    // Método para rechazo por SPAM (usado por el servicio)
    public void rechazarPorSpam(Administrador administradorSistema) {
        this.estado = EstadoSolicitud.RECHAZADA_SPAM;
        registrarRevision(administradorSistema);
    }
}