package ar.edu.utn.frba.dds.models.entities.hecho;

import ar.edu.utn.frba.dds.models.entities.TipoDeFuente;

public class FuenteMetaData {
    String titulo;
    String descripcion;
    TipoDeFuente tipo;

    public FuenteMetaData(String titulo, TipoDeFuente tipo) {
        this.titulo = titulo;
        this.tipo = tipo;
    }

    public FuenteMetaData(String importadorCsv, ar.edu.utn.frba.dds.models.entities.fuente.TipoDeFuente tipoDeFuente) {
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public TipoDeFuente getTipo() {
        return tipo;
    }

    public void setTipo(TipoDeFuente tipo) {
        this.tipo = tipo;
    }
}
