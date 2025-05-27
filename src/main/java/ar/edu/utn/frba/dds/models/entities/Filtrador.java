package ar.edu.utn.frba.dds.models.entities;

import ar.edu.utn.frba.dds.models.entities.hecho.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class Filtrador {
    private Boolean eliminado;
    private String titulo;
    private String descripcion;
    private Categoria categoria;
    private LocalDate fechaAcontecimiento;
    private LocalDate fechaCarga;
    private Contribuyente contribuyente;
    private TipoDeFuente origen;
    private List<Etiqueta> etiquetas;
    private Ubicacion ubicacion;

    public Filtrador() {}


    public List<Hecho> filtrar(List<Hecho> hechos) {
        return hechos.stream()
                .filter(h -> categoria == null || h.getCategoria().compararCategoria(categoria))
                .filter(h -> titulo == null || h.getTitulo().equals(titulo))
                .filter(h -> ubicacion == null || h.getUbicacion().compararUbicacion(ubicacion))
                .filter(h -> etiquetas == null || h.getEtiquetas().containsAll(etiquetas))
                .collect(Collectors.toList());
    }

    public void setEliminado(Boolean eliminado) {
        this.eliminado = eliminado;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public void setFechaCarga(LocalDate fechaCarga) {
        this.fechaCarga = fechaCarga;
    }

    public void setFechaAcontecimiento(LocalDate fechaAcontecimiento) {
        this.fechaAcontecimiento = fechaAcontecimiento;
    }

    public void setContribuyente(Contribuyente contribuyente) {
        this.contribuyente = contribuyente;
    }

    public void setOrigen(TipoDeFuente origen) {
        this.origen = origen;
    }

    public void setEtiquetas(List<Etiqueta> etiquetas) {
        this.etiquetas = etiquetas;
    }

    public void setUbicacion(Ubicacion ubicacion) {
        this.ubicacion = ubicacion;
    }
}

