package ar.edu.utn.frba.dds.models.repositories.impl;


import ar.edu.utn.frba.dds.models.entities.hecho.Hecho;
import ar.edu.utn.frba.dds.models.repositories.IHechosRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HechosRepository implements IHechosRepository {

    private final Map<String, Hecho> hechos = new HashMap<>();

    @Override
    public void guardar(Hecho hecho) {
        hechos.put(hecho.getTitulo(), hecho);
    }

    @Override
    public Hecho findById(Long id) {
        return hechos.values().stream()
                .filter(h -> h.hashCode() == id.intValue())
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<Hecho> obtenerTodos() {
        return new ArrayList<>(hechos.values());
    }
}
