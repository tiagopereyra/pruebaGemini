package ar.edu.utn.frba.dds.models.repositories;

import ar.edu.utn.frba.dds.models.entities.Hecho;
import java.util.List;
import java.util.Optional;

public interface HechoRepository {
    Hecho guardar(Hecho hecho);
    Optional<Hecho> buscarPorId(String id);
    List<Hecho> buscarTodos();
}
