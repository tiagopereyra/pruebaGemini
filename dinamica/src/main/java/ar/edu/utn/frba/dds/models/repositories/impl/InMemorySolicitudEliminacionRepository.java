package ar.edu.utn.frba.dds.models.repositories.impl;

import ar.edu.utn.frba.dds.models.entities.SolicitudEliminacion;
import ar.edu.utn.frba.dds.models.repositories.SolicitudEliminacionRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class InMemorySolicitudEliminacionRepository implements SolicitudEliminacionRepository {
    private final Map<String, SolicitudEliminacion> solicitudes = new ConcurrentHashMap<>();

    @Override
    public SolicitudEliminacion guardar(SolicitudEliminacion solicitud) {
        solicitudes.put(solicitud.getId(), solicitud);
        return solicitud;
    }

    @Override
    public Optional<SolicitudEliminacion> buscarPorId(String id) {
        return Optional.ofNullable(solicitudes.get(id));
    }

    @Override
    public List<SolicitudEliminacion> buscarTodas() {
        return Collections.unmodifiableList(new ArrayList<>(solicitudes.values()));
    }

    @Override
    public void eliminar(String id) {
        solicitudes.remove(id);
    }
}