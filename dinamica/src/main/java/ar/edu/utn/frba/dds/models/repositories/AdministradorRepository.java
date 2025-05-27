package ar.edu.utn.frba.dds.models.repositories;

import ar.edu.utn.frba.dds.models.entities.Administrador;
import java.util.Optional;

public interface AdministradorRepository {
    Optional<Administrador> buscarPorId(String id);
    void agregar(Administrador administrador);
}