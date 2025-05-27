// Primero, modifica SolicitudEliminacion.java para que tenga un ID:
// package ar.edu.utn.frba.dds.models.entities;
// import java.util.UUID; // Añadir import
// public class SolicitudEliminacion {
//     private String id; //Añadir este campo
//     // ... resto de tus campos ...
//
//     public SolicitudEliminacion(String motivo, Hecho hecho) {
//         this.id = UUID.randomUUID().toString(); // Asignar ID en constructor
//         // ... resto de tu constructor ...
//     }
//
//     public String getId() { // Añadir getter
//         return id;
//     }
//     // ... resto de tu clase ...
// }

package ar.edu.utn.frba.dds.models.repositories;

import ar.edu.utn.frba.dds.models.entities.SolicitudEliminacion;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

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
}