package ar.edu.utn.frba.dds.models.repositories;

import ar.edu.utn.frba.dds.models.entities.Administrador; // Asegúrate que Administrador.java exista
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryAdministradorRepository implements AdministradorRepository {
    private final Map<String, Administrador> administradores = new ConcurrentHashMap<>();

    public InMemoryAdministradorRepository() {
        // Opcional: Puedes pre-cargar algunos administradores para facilitar las pruebas
        // Ejemplo:
        // agregar(new Administrador("admin001", "Admin Principal"));
        // agregar(new Administrador("admin_sistema_spam", "Sistema AntiSpam"));
    }

    @Override
    public Optional<Administrador> buscarPorId(String id) {
        if (id == null) {
            return Optional.empty();
        }
        return Optional.ofNullable(administradores.get(id));
    }

    @Override
    public void agregar(Administrador administrador) {
        if (administrador != null && administrador.getId() != null) {
            administradores.put(administrador.getId(), administrador);
        }
    }
}