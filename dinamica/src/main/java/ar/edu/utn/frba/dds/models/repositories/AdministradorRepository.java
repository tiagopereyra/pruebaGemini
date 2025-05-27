package ar.edu.utn.frba.dds.models.repositories;

import ar.edu.utn.frba.dds.models.entities.Administrador; // Asegúrate que Administrador.java exista y esté en el paquete correcto
import java.util.Optional;

public interface AdministradorRepository {
    Optional<Administrador> buscarPorId(String id);
    void agregar(Administrador administrador); // Para poder añadir administradores al repositorio en memoria
}