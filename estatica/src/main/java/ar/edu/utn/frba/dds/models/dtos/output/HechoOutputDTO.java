package ar.edu.utn.frba.dds.models.dtos.output;

import ar.edu.utn.frba.dds.models.entities.hecho.Hecho;
import lombok.Data;

import java.time.LocalDate;

@Data
public class HechoOutputDTO {
    private long id;
    private String titulo;
    private String descripcion;
    private LocalDate fechaAcontecimiento;
    private CategoriaDTO categoria;
    private UbicacionDTO ubicacion;

    public void setTitulo(String titulo) {
    }

    public void setDescripcion(String descripcion) {
    }

    public void setCategoria(CategoriaDTO categoriaDTO) {
    }

    public void setFechaAcontecimiento(LocalDate fechaAcontecimiento) {
    }

    public void setUbicacion(UbicacionDTO ubicacionDTO) {
    }

    public void setId(Object id) {
    }
}

