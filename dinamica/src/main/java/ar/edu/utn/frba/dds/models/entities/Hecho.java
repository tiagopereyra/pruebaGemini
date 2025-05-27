package ar.edu.utn.frba.dds.models.entities;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class Hecho {
    private String identificador;
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

    public Hecho(String titulo, String descripcion, Categoria categoria,
                 LocalDate fechaAcontecimiento, Contribuyente contribuyente,
                 TipoDeFuente origen, Ubicacion ubicacion, Multimedia contenidoMultimedia) {
        this.identificador = UUID.randomUUID().toString();
        this.eliminado = false;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.categoria = categoria;
        this.fechaAcontecimiento = fechaAcontecimiento;
        this.contribuyente = contribuyente;
        this.origen = origen;
        this.ubicacion = ubicacion;
        this.contenidoMultimedia = contenidoMultimedia;
        this.etiquetas = new ArrayList<>();
        this.solicitudesPendientes = new ArrayList<>();
        this.fechaCarga = LocalDate.now();
        this.revision = new RevisionHecho();
    }

    public Hecho(String titulo, String descripcion, Categoria categoria,
                 LocalDate fechaAcontecimiento, TipoDeFuente origen,
                 Ubicacion ubicacion, Multimedia contenidoMultimedia) {
        this(titulo, descripcion, categoria, fechaAcontecimiento, null, origen, ubicacion, contenidoMultimedia);
    }


    public void agregarEtiqueta(Etiqueta etiqueta) {
        if (this.etiquetas == null) {
            this.etiquetas = new ArrayList<>();
        }
        this.etiquetas.add(etiqueta);
    }

    public void marcarEliminado() {
        this.eliminado = true;
    }

    public void agregarSolicitud(SolicitudEliminacion solicitud) {
        if (this.solicitudesPendientes == null) {
            this.solicitudesPendientes = new ArrayList<>();
        }
        this.solicitudesPendientes.add(solicitud);
    }

    public void removerSolicitud(SolicitudEliminacion solicitud) {
        if (this.solicitudesPendientes != null) {
            this.solicitudesPendientes.remove(solicitud);
        }
    }
}