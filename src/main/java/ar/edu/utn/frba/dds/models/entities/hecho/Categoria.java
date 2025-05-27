package ar.edu.utn.frba.dds.models.entities.hecho;

public class Categoria {
    private String nombre;

    public Categoria(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public boolean compararCategoria(Categoria categoria) {
        return categoria.getNombre().equals(nombre);
    }
}
