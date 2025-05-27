package ar.edu.utn.frba.dds.services;


import ar.edu.utn.frba.dds.models.dtos.output.HechoOutputDTO;
import java.util.List;


public interface IHechosService {
    List<HechoOutputDTO> buscarTodos(String csvPath);
    HechoOutputDTO buscarPorId(Long id);
    Boolean eliminar(String titulo, String csvPath);
    Boolean agregarFuente(String csvPath);
    Boolean agregarColumnaId(String csvPath);
    List<HechoOutputDTO> obtenerNuevosHechos(String csvPath);
}