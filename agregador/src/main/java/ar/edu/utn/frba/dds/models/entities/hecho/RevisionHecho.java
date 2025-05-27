package ar.edu.utn.frba.dds.models.entities.hecho;

import ar.edu.utn.frba.dds.models.entities.Administrador;

import java.util.Date;

public class RevisionHecho {

    private String hechoAgregadoID;
    private String hechoAnteriorID;
    private Administrador administrador;
    private Date fechaRevisado;
    private String sugerencia;

    public void aprobar(Administrador administrador){
        //TODO acer que se apruebe un hecho o una modificacion de un hecho
        // en caso de aprobar una modificación se pone en oculto el hecho anterior.
    }

    public void aprobar(Administrador administrador, String sugerencia){

    }

    public void rechazar(Administrador administrador){
        // TODO hacer que se rechace el hecho
    }
}
