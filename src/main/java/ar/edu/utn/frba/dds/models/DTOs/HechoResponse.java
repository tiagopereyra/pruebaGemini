package ar.edu.utn.frba.dds.models.DTOs;

import ar.edu.utn.frba.dds.models.entities.*;
import ar.edu.utn.frba.dds.models.entities.hecho.*;

import java.time.LocalDate;
import java.util.List;

public class HechoResponse {
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
}
