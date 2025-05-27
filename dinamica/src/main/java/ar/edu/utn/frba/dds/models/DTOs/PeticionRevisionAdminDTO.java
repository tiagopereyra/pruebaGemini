package ar.edu.utn.frba.dds.models.DTOs;

import ar.edu.utn.frba.dds.models.entities.EstadoRevision;

public class PeticionRevisionAdminDTO {
    private String idHecho;
    private String idAdministrador; // ID del admin que revisa
    private EstadoRevision decision; // APROBADO, APROBADO_CON_SUGERENCIAS, RECHAZADO
    private String sugerencias; // Solo si la decisión es APROBADO_CON_SUGERENCIAS

    // Getters y Setters...
    public String getIdHecho() { return idHecho; }
    public void setIdHecho(String idHecho) { this.idHecho = idHecho; }
    public String getIdAdministrador() { return idAdministrador; }
    public void setIdAdministrador(String idAdministrador) { this.idAdministrador = idAdministrador; }
    public EstadoRevision getDecision() { return decision; }
    public void setDecision(EstadoRevision decision) { this.decision = decision; }
    public String getSugerencias() { return sugerencias; }
    public void setSugerencias(String sugerencias) { this.sugerencias = sugerencias; }
}
