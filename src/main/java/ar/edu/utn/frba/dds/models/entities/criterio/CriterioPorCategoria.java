package ar.edu.utn.frba.dds.models.entities.criterio;

import ar.edu.utn.frba.dds.models.entities.hecho.Categoria;
import ar.edu.utn.frba.dds.models.entities.hecho.Hecho;

public class CriterioPorCategoria implements Criterio{

    public Categoria categoria;

    public CriterioPorCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    @Override
    public boolean evaluarPertenencia(Hecho hecho){
        return hecho.getCategoria().getNombre().equals(this.categoria.getNombre());
    }
}
