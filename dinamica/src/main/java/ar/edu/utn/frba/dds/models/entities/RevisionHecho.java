package ar.edu.utn.frba.dds.models.entities;

import java.time.LocalDate; // Es más común usar LocalDate para fechas sin hora

public class RevisionHecho {

    private String hechoAgregadoID; // ID del hecho que se está revisando (si es una nueva versión)
    private String hechoAnteriorID; // ID del hecho original (si esto es una revisión de una modificación)
    private Administrador administrador;
    private LocalDate fechaRevisado; // Cambiado a LocalDate
    private String sugerencia;
    private EstadoRevision estado; // Nuevo campo

    // Constructor
    public RevisionHecho() {
        this.estado = EstadoRevision.PENDIENTE; // Por defecto, una nueva revisión está pendiente
        // Los otros campos se establecerán cuando un administrador actúe.
    }

    // Getters
    public String getHechoAgregadoID() {
        return hechoAgregadoID;
    }

    public String getHechoAnteriorID() {
        return hechoAnteriorID;
    }

    public Administrador getAdministrador() {
        return administrador;
    }

    public LocalDate getFechaRevisado() {
        return fechaRevisado;
    }

    public String getSugerencia() {
        return sugerencia;
    }

    public EstadoRevision getEstado() {
        return estado;
    }

    // Setters (útiles para el servicio de administración)
    public void setHechoAgregadoID(String hechoAgregadoID) {
        this.hechoAgregadoID = hechoAgregadoID;
    }

    public void setHechoAnteriorID(String hechoAnteriorID) {
        this.hechoAnteriorID = hechoAnteriorID;
    }

    public void setAdministrador(Administrador administrador) {
        this.administrador = administrador;
    }

    public void setFechaRevisado(LocalDate fechaRevisado) {
        this.fechaRevisado = fechaRevisado;
    }

    public void setSugerencia(String sugerencia) {
        this.sugerencia = sugerencia;
    }

    public void setEstado(EstadoRevision estado) {
        this.estado = estado;
    }

    // Métodos de acción actualizados
    // Estos métodos ahora actualizan el estado interno de esta instancia de RevisionHecho.
    // El servicio (`AdministracionFuentesService`) será el encargado de llamar a estos métodos
    // y luego persistir los cambios en el Hecho y su RevisionHecho.

    /**
     * Marca la revisión como aprobada.
     * @param admin El administrador que realiza la aprobación.
     */
    public void aprobar(Administrador admin) {
        this.administrador = admin;
        this.fechaRevisado = LocalDate.now();
        this.estado = EstadoRevision.APROBADO;
        this.sugerencia = null; // Limpiar sugerencias si se aprueba directamente
        // La lógica de qué sucede con el Hecho asociado (ej. hacerlo visible)
        // se manejará en el AdministracionFuentesService.
    }

    /**
     * Marca la revisión como aprobada con sugerencias.
     * @param admin El administrador que realiza la aprobación.
     * @param sugerenciaTexto La sugerencia para el contribuyente.
     */
    public void aprobarConSugerencias(Administrador admin, String sugerenciaTexto) {
        this.administrador = admin;
        this.fechaRevisado = LocalDate.now();
        this.estado = EstadoRevision.APROBADO_CON_SUGERENCIAS;
        this.sugerencia = sugerenciaTexto;
        // La lógica de qué sucede con el Hecho asociado
        // se manejará en el AdministracionFuentesService.
    }

    /**
     * Marca la revisión como rechazada.
     * @param admin El administrador que realiza el rechazo.
     */
    public void rechazar(Administrador admin) {
        this.administrador = admin;
        this.fechaRevisado = LocalDate.now();
        this.estado = EstadoRevision.RECHAZADO;
        this.sugerencia = null; // Opcionalmente, se podría añadir un motivo de rechazo aquí también.
        // La lógica de qué sucede con el Hecho asociado (ej. marcarlo como no visible o eliminado lógicamente)
        // se manejará en el AdministracionFuentesService.
    }
}