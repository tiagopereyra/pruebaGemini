package ar.edu.utn.frba.dds.services;

import ar.edu.utn.frba.dds.models.DTOs.input.ColeccionDTO;
import ar.edu.utn.frba.dds.models.DTOs.output.HechoDTO;
import ar.edu.utn.frba.dds.models.DTOs.output.SolicitudEliminacionDTO;

import java.util.List;

public interface IColeccionService {
    public void actualizarHechos();
    public List<HechoDTO> devolverHechosDeColeccion(String coleccionID);
    public boolean guardarSolicitudesEliminacion(SolicitudEliminacionDTO solicitudDTO);
    public boolean guardarColeccion(ColeccionDTO coleccionDTO);
}
