package ar.edu.utn.frba.dds.models.repositories.impl;

import ar.edu.utn.frba.dds.models.entities.Hecho;
import ar.edu.utn.frba.dds.models.repositories.HechoRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class InMemoryHechoRepository implements HechoRepository {
    private final Map<String, Hecho> hechos = new ConcurrentHashMap<>();

    @Override
    public Hecho guardar(Hecho hecho) {
        if (hecho.getIdentificador() == null || hecho.getIdentificador().trim().isEmpty()) {
            // Esto ya se hace en el constructor de Hecho, pero por si acaso
            // hecho.setIdentificador(UUID.randomUUID().toString());
        }
        hechos.put(hecho.getIdentificador(), hecho);
        return hecho;
    }

    @Override
    public Optional<Hecho> buscarPorId(String id) {
        return Optional.ofNullable(hechos.get(id));
    }

    @Override
    public List<Hecho> buscarTodos() {
        return Collections.unmodifiableList(new ArrayList<>(hechos.values()));
    }
}