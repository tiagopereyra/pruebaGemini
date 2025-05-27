package ar.edu.utn.frba.dds.models.entities;

// El import "ar.edu.utn.frba.dds.models.entities.*;" es redundante si estás en el mismo paquete.
// Lo he comentado/eliminado. Si tus clases estuvieran en subpaquetes, sería necesario.

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
// import java.util.Objects; // Podría ser útil para equals/hashCode si lo implementas

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

    // Constructores
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
        this.revision = new RevisionHecho(); // Inicializar la revisión
    }

    public Hecho(String titulo, String descripcion, Categoria categoria,
                 LocalDate fechaAcontecimiento,
                 TipoDeFuente origen, Ubicacion ubicacion) {
        this.eliminado = false;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.categoria = categoria;
        this.fechaAcontecimiento = fechaAcontecimiento;
        this.contribuyente = null; // Explícitamente anónimo
        this.origen = origen;
        this.ubicacion = ubicacion;
        this.solicitudesPendientes = new ArrayList<>();
        this.etiquetas = new ArrayList<>();
        this.fechaCarga = LocalDate.now();
        this.revision = new RevisionHecho(); // Inicializar la revisión
    }

    // Getters
    public String getIdentificador() {
        return identificador;
    }

    public boolean isEliminado() { // Renombrado de estaEliminado para seguir convención
        return eliminado;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public Multimedia getContenidoMultimedia() {
        return contenidoMultimedia;
    }

    public LocalDate getFechaAcontecimiento() {
        return fechaAcontecimiento;
    }

    public LocalDate getFechaCarga() {
        return fechaCarga;
    }

    public List<SolicitudEliminacion> getSolicitudesPendientes() {
        return solicitudesPendientes;
    }

    public Contribuyente getContribuyente() {
        return contribuyente;
    }

    public TipoDeFuente getOrigen() {
        return origen;
    }

    public List<Etiqueta> getEtiquetas() {
        return etiquetas;
    }

    public Ubicacion getUbicacion() {
        return ubicacion;
    }

    public RevisionHecho getRevision() {
        return revision;
    }

    // Setters solicitados y otros necesarios
    public void setIdentificador(String identificador) {
        this.identificador = identificador;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setCategoria(Categoria nuevaCategoria) {
        this.categoria = nuevaCategoria;
    }

    public void setFechaAcontecimiento(LocalDate fechaAcontecimiento) {
        this.fechaAcontecimiento = fechaAcontecimiento;
    }

    public void setUbicacion(Ubicacion nuevaUbicacion) {
        this.ubicacion = nuevaUbicacion;
    }

    public void setContenidoMultimedia(Multimedia contenidoMultimedia) {
        this.contenidoMultimedia = contenidoMultimedia;
    }

    public void setRevision(RevisionHecho revision) {
        this.revision = revision;
    }

    // Métodos de lógica de negocio
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

    public void eliminarSolicitud(SolicitudEliminacion solicitud) {
        if (this.solicitudesPendientes != null) {
            this.solicitudesPendientes.remove(solicitud);
        }
    }
}