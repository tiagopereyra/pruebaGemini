package ar.edu.utn.frba.dds.models.entities.hecho;

import ar.edu.utn.frba.dds.models.entities.*;

import java.time.LocalDate;
import java.util.*;

public class Hecho {
    private Integer identificador;
    private boolean eliminado;
    private String titulo;
    private String descripcion;
    private Categoria categoria;
    private Multimedia contenidoMultimedia;
    private LocalDate fechaAcontecimiento;
    private LocalDate fechaCarga;
    private List<SolicitudEliminacion> solicitudesPendientes;
    private Contribuyente contribuyente;
    private TipoDeFuente origen;
    private List<Etiqueta> etiquetas;
    private Ubicacion ubicacion;
    private RevisionHecho revision;

    public LocalDate getFechaAcontecimiento() {
        return fechaAcontecimiento;
    }

    public Hecho(String titulo, String descripcion, Categoria categoria,
                 LocalDate fechaAcontecimiento, Contribuyente contribuyente,
                 TipoDeFuente origen, Ubicacion ubicacion) {
        this.eliminado = false;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.categoria = categoria;
        this.fechaAcontecimiento = fechaAcontecimiento;
        this.contribuyente = contribuyente;
        this.origen = origen;
        this.ubicacion = ubicacion;
        this.etiquetas = new ArrayList<>();
        this.solicitudesPendientes = new ArrayList<>();
        this.fechaCarga = LocalDate.now();
    }

    public Hecho(String titulo, String descripcion, Categoria categoria,
                 LocalDate fechaAcontecimiento,
                 TipoDeFuente origen, Ubicacion ubicacion){
        this.eliminado = false;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.categoria = categoria;
        this.fechaAcontecimiento = fechaAcontecimiento;
        this.contribuyente = null;
        this.origen = origen;
        this.ubicacion = ubicacion;
        this.solicitudesPendientes = new ArrayList<>();
        this.etiquetas = new ArrayList<>();
        this.fechaCarga = LocalDate.now();
    }

    public void agregarEtiqueta(Etiqueta etiqueta) {
        etiquetas.add(etiqueta);
    }

    public String getTitulo() {
        return titulo;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public LocalDate getFechaCarga(){ return fechaCarga; }


    public List<Etiqueta> getEtiquetas() {
        return etiquetas;
    }

    public void marcarEliminado(){ this.eliminado = true;}

    public boolean estaEliminado(){return this.eliminado;}

    public void agregarSolicitud(SolicitudEliminacion solicitud) {
        this.solicitudesPendientes.add(solicitud);
    }

    public void eliminarSolicitud(SolicitudEliminacion solicitud){
        this.solicitudesPendientes.remove(solicitud);
    }


    public boolean isEliminado() {
        return eliminado;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public Contribuyente getContribuyente() {
        return contribuyente;
    }

    public Ubicacion getUbicacion() {
        return ubicacion;
    }

    public TipoDeFuente getOrigen() {
        return origen;
    }

    public RevisionHecho getRevision() {
        return revision;
    }

    public void setRevision(RevisionHecho revision) {
        this.revision = revision;
    }

    public Integer getIdentificador() {
        return identificador;
    }

    public void setIdentificador(Integer identificador) {
        this.identificador = identificador;
    }
}
