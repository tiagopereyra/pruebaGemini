package ar.edu.utn.frba.dds.models.entities;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Multimedia {
    private String tipo;
    private String referencia;

    public Multimedia(String tipo, String referencia) {
        this.tipo = tipo;
        this.referencia = referencia;
    }

    public Multimedia() {
    }
}
