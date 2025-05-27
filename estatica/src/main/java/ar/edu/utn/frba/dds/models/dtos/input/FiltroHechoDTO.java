package ar.edu.utn.frba.dds.models.dtos.input;


import lombok.Data;

import java.time.LocalDate;

@Data

public class FiltroHechoDTO {
    private String categoria;
    private LocalDate desde;
    private LocalDate hasta;
    private String csvPath;

    public String getCsvPath() {
        return null;
    }
}