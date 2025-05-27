package ar.edu.utn.frba.dds.models.repositories.impl;

import ar.edu.utn.frba.dds.models.entities.Contribuyente;
import ar.edu.utn.frba.dds.models.repositories.ContribuyenteRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class InMemoryContribuyenteRepository implements ContribuyenteRepository {
    private final Map<String, Contribuyente> contribuyentes = new ConcurrentHashMap<>();

    @Override
    public Contribuyente guardar(Contribuyente contribuyente) {
        if (contribuyente.getId() == null || contribuyente.getId().trim().isEmpty()) {
            contribuyente.setId(UUID.randomUUID().toString());
        }
        contribuyentes.put(contribuyente.getId(), contribuyente);
        return contribuyente;
    }

    @Override
    public Optional<Contribuyente> buscarPorId(String id) {
        return Optional.ofNullable(contribuyentes.get(id));
    }

    @Override
    public List<Contribuyente> buscarTodos() {
        return Collections.unmodifiableList(new ArrayList<>(contribuyentes.values()));
    }
}
