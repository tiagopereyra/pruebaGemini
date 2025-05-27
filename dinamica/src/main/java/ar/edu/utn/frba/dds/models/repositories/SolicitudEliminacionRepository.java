package ar.edu.utn.frba.dds.models.repositories;

import ar.edu.utn.frba.dds.models.entities.SolicitudEliminacion;
import java.util.List;
import java.util.Optional;

public interface SolicitudEliminacionRepository {
    SolicitudEliminacion guardar(SolicitudEliminacion solicitud);
    Optional<SolicitudEliminacion> buscarPorId(String id); // Asumiendo que Solicitud tenga ID
    List<SolicitudEliminacion> buscarTodas();
    // Podrías necesitar más métodos, ej. buscarPorHechoId, buscarPorEstado
}