package ar.edu.utn.frba.dds.models.entities.criterio;

import ar.edu.utn.frba.dds.models.entities.hecho.Hecho;

import java.util.List;

public class CriterioAND implements Criterio{
    private List<Criterio> criterios;

    public void agregarCriterio(Criterio criterio){
        criterios.add(criterio);
    }

    @Override
    public boolean evaluarPertenencia(Hecho hecho){
        return criterios.stream().allMatch(c -> c.evaluarPertenencia(hecho));
    }
}
