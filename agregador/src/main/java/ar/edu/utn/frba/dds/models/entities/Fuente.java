package ar.edu.utn.frba.dds.models.entities;

import ar.edu.utn.frba.dds.models.entities.hecho.Hecho;
import ar.edu.utn.frba.dds.models.entities.hecho.TipoDeFuente;
import lombok.Getter;

import java.util.List;
import java.util.Map;

@Getter
public class Fuente {
    private String uri;
    private String nombreFuente;
    private String descripcion;
    private TipoDeFuente Tipo;
    private Map<String, String> parametros;

    public Fuente(String uri, String nombreFuente, String descripcion, TipoDeFuente tipo, Map<String, String> parametros) {
        this.uri = uri;
        this.nombreFuente = nombreFuente;
        this.descripcion = descripcion;
        Tipo = tipo;
        this.parametros = parametros;
    }
}
