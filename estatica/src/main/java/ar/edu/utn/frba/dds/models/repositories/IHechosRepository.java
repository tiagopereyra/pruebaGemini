package ar.edu.utn.frba.dds.models.repositories;


import ar.edu.utn.frba.dds.models.entities.hecho.Hecho;

import java.util.List;

public interface IHechosRepository {
    void guardar(Hecho hecho);
    Hecho findById(Long id);
    List<Hecho> obtenerTodos();
}