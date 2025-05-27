package ar.edu.utn.frba.dds.models.entities.criterio;

import ar.edu.utn.frba.dds.models.entities.hecho.Hecho;

public interface Criterio {
    boolean evaluarPertenencia(Hecho hecho);
}
