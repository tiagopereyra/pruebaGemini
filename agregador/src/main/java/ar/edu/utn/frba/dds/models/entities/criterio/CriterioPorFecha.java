package ar.edu.utn.frba.dds.models.entities.criterio;

import ar.edu.utn.frba.dds.models.entities.hecho.Hecho;

import java.time.LocalDate;

public class CriterioPorFecha implements Criterio{

    public LocalDate fechaInicio;
    public LocalDate fechaFinal;

    public CriterioPorFecha(LocalDate fechaInicio, LocalDate fechaFinal) {
        this.fechaInicio = fechaInicio;
        this.fechaFinal = fechaFinal;
    }

    @Override
    public boolean evaluarPertenencia(Hecho hecho){
        return (hecho.getFechaAcontecimiento().equals(fechaInicio) || hecho.getFechaAcontecimiento().isAfter(fechaInicio)) &&
                (hecho.getFechaAcontecimiento().equals(fechaFinal) || hecho.getFechaAcontecimiento().isBefore(fechaFinal));
    }
}
