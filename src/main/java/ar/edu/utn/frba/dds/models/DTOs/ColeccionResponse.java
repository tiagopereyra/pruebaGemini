package ar.edu.utn.frba.dds.models.DTOs;

import ar.edu.utn.frba.dds.models.entities.fuente.Fuente;
import ar.edu.utn.frba.dds.models.entities.hecho.Hecho;

import java.util.List;

public class ColeccionResponse {
    private String titulo;
    private String descripcion;
    private Fuente fuente;
    private List<Hecho> listaHechos;
}
