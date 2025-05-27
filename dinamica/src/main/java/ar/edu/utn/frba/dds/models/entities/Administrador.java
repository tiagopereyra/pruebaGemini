package ar.edu.utn.frba.dds.models.entities;

public class Administrador {
    private String id;
    private String nombre;

    public Administrador(String id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public String getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    // Podrías añadir equals y hashCode si necesitas comparar administradores
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Administrador that = (Administrador) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
