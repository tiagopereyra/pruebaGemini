package ar.edu.utn.frba.dds.models.entities.hecho;

import ar.edu.utn.frba.dds.models.entities.fuente.Fuente;

import java.time.LocalDate;


public class Hecho {

    private long id;
    private String titulo;
    private String descripcion;
    private LocalDate fechaAcontecimiento;
    private Categoria categoria;
    private Fuente fuente;
    private Ubicacion ubicacion;
    private Boolean eliminado;

    public Hecho(String titulo, String descripcion, Categoria categoria, LocalDate fecha, FuenteMetaData fuenteMeta, Ubicacion ubicacion) {
    }

    public String getTitulo() {
        return titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public LocalDate getFechaAcontecimiento() {
        return fechaAcontecimiento;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public Fuente getFuente() {
        return fuente;
    }

    public Ubicacion getUbicacion() {
        return ubicacion;
    }

    public void setEliminado(Boolean eliminado) {
        this.eliminado = eliminado;
    }

    public Object getId() {
        return null;
    }

    public void setId(long id) {
    }

    public boolean getEliminado() {
    }
}
