package ar.edu.utn.frba.dds.models.repositories;

import ar.edu.utn.frba.dds.models.entities.Contribuyente;
import java.util.Optional;
import java.util.List;

public interface ContribuyenteRepository {
    Contribuyente guardar(Contribuyente contribuyente);
    Optional<Contribuyente> buscarPorId(String id);
    List<Contribuyente> buscarTodos();
}
