package ar.edu.utn.frba.dds.services;

import ar.edu.utn.frba.dds.models.entities.Multimedia;
import org.springframework.stereotype.Service;

@Service
public class GestionMultimediaService {

    public Multimedia procesarYGuardar(String tipoMultimedia, String datosMultimedia) {
        if (datosMultimedia == null || datosMultimedia.isEmpty()) {
            return null;
        }
        Multimedia multimedia = new Multimedia();
        multimedia.setTipo(tipoMultimedia);
        multimedia.setReferencia(datosMultimedia);
        return multimedia;
    }
}
