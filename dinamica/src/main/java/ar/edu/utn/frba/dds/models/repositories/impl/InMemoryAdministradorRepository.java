package ar.edu.utn.frba.dds.models.repositories.impl;

import ar.edu.utn.frba.dds.models.entities.Administrador;
import ar.edu.utn.frba.dds.models.repositories.AdministradorRepository;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class InMemoryAdministradorRepository implements AdministradorRepository {
    private final Map<String, Administrador> administradores = new ConcurrentHashMap<>();

    public InMemoryAdministradorRepository() {
    }

    @Override
    public Optional<Administrador> buscarPorId(String id) {
        if (id == null) {
            return Optional.empty();
        }
        return Optional.ofNullable(administradores.get(id));
    }

    @Override
    public void agregar(Administrador administrador) {
        if (administrador != null && administrador.getId() != null) {
            administradores.put(administrador.getId(), administrador);
        }
    }
}