package ar.edu.utn.frba.dds.models.repositories;

import ar.edu.utn.frba.dds.models.entities.Coleccion;

import java.util.List;

public interface IColeccionRepositorie {
    Coleccion save(Coleccion coleccion);
    List<Coleccion> findAll();
    Coleccion findById(String ID);
}
