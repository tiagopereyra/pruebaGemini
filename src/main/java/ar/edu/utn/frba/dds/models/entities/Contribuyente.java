package ar.edu.utn.frba.dds.models.entities;

import java.time.LocalDate;

public class Contribuyente {
    private String nombre;
    private String apellido;
    private LocalDate fechaNacimiento;

    public Contribuyente(String nombre, String apellido, LocalDate fechaNacimiento) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.fechaNacimiento = fechaNacimiento;
    }
}