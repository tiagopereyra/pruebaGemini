package ar.edu.utn.frba.dds.models.entities;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Categoria {
    private String nombre;

    public Categoria(String nombre) {
        this.nombre = nombre;
    }

    public boolean compararCategoria(Categoria categoria) {
        return categoria != null && this.nombre.equals(categoria.getNombre());
    }
}
