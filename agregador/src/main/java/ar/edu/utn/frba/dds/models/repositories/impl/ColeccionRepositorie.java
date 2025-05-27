package ar.edu.utn.frba.dds.models.repositories.impl;

import ar.edu.utn.frba.dds.models.entities.Coleccion;
import ar.edu.utn.frba.dds.models.repositories.IColeccionRepositorie;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ColeccionRepositorie implements IColeccionRepositorie {
    private final Map<Long, Coleccion> colecciones = new HashMap<>();

    @Override
    public Coleccion save(Coleccion coleccion) {
        // TODO hacer el método guardar en colecciones repositorio
        return null;
    }

    @Override
    public List<Coleccion> findAll() {
        return new ArrayList<>(colecciones.values()) ;
    }

    @Override
    public Coleccion findById(String ID) {
        // TODO hacer el findById de colecciones repositorio
        return null;
    }
}
