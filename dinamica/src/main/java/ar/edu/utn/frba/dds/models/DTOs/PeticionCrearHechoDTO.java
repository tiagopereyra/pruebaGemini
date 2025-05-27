package ar.edu.utn.frba.dds.models.DTOs;

import java.time.LocalDate;

public class PeticionCrearHechoDTO {
    private String titulo;
    private String descripcion;
    private String nombreCategoria; // Usaremos el nombre para buscar/crear la Categoria
    private LocalDate fechaAcontecimiento;
    private double latitudUbicacion;
    private double longitudUbicacion;

    // Para contenido multimedia (simplificado por ahora)
    private String tipoMultimedia; // ej. "imagen", "video", "texto"
    private String datosMultimedia; // ej. URL, path local, o el texto mismo si es un hecho de texto

    // Datos del contribuyente (opcionales, si es null o nombre vacío, es anónimo)
    private String nombreContribuyente;
    private String apellidoContribuyente;
    private LocalDate fechaNacimientoContribuyente;

    // Getters y Setters para todos los campos...

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
    public String getNombreContribuyente() { return nombreContribuyente; }
    public void setNombreContribuyente(String nombreContribuyente) { this.nombreContribuyente = nombreContribuyente; }
    public String getApellidoContribuyente() { return apellidoContribuyente; }
    public void setApellidoContribuyente(String apellidoContribuyente) { this.apellidoContribuyente = apellidoContribuyente; }
    public LocalDate getFechaNacimientoContribuyente() { return fechaNacimientoContribuyente; }
    public void setFechaNacimientoContribuyente(LocalDate fechaNacimientoContribuyente) { this.fechaNacimientoContribuyente = fechaNacimientoContribuyente; }
}