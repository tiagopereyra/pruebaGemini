package ar.edu.utn.frba.dds.models.DTOs;

import java.time.LocalDate;

public class PeticionModificarHechoDTO {
    // Los mismos campos que PeticionCrearHechoDTO, excepto los del contribuyente,
    // ya que el contribuyente original no se puede cambiar.
    private String titulo;
    private String descripcion;
    private String nombreCategoria;
    private LocalDate fechaAcontecimiento;
    private double latitudUbicacion;
    private double longitudUbicacion;
    private String tipoMultimedia;
    private String datosMultimedia;

    // Getters y Setters...
    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public String getNombreCategoria() { return nombreCategoria; }
    public void setNombreCategoria(String nombreCategoria) { this.nombreCategoria = nombreCategoria; }
    public LocalDate getFechaAcontecimiento() { return fechaAcontecimiento; }
    public void setFechaAcontecimiento(LocalDate fechaAcontecimiento) { this.fechaAcontecimiento = fechaAcontecimiento; }
    public double getLatitudUbicacion() { return latitudUbicacion; }
    public void setLatitudUbicacion(double latitudUbicacion) { this.latitudUbicacion = latitudUbicacion; }
    public double getLongitudUbicacion() { return longitudUbicacion; }
    public void setLongitudUbicacion(double longitudUbicacion) { this.longitudUbicacion = longitudUbicacion; }
    public String getTipoMultimedia() { return tipoMultimedia; }
    public void setTipoMultimedia(String tipoMultimedia) { this.tipoMultimedia = tipoMultimedia; }
    public String getDatosMultimedia() { return datosMultimedia; }
    public void setDatosMultimedia(String datosMultimedia) { this.datosMultimedia = datosMultimedia; }
}