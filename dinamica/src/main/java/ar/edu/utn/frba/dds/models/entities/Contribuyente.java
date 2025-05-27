package ar.edu.utn.frba.dds.models.entities;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;
import java.util.Objects;


@Getter
@Setter
public class Contribuyente {
    private String id;
    private String nombre;
    private String apellido;
    private LocalDate fechaNacimiento;

    public Contribuyente(String id, String nombre, String apellido, LocalDate fechaNacimiento) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.fechaNacimiento = fechaNacimiento;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Contribuyente that = (Contribuyente) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}