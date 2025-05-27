package ar.edu.utn.frba.dds.models.entities.fuente;

import ar.edu.utn.frba.dds.models.entities.hecho.Hecho;

import java.util.List;

public interface Fuente {
    List<Hecho> obtenerHechos();
}
