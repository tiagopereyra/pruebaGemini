package ar.edu.utn.frba.dds.controllers;


import ar.edu.utn.frba.dds.models.dtos.input.FiltroHechoDTO;
import ar.edu.utn.frba.dds.models.dtos.output.HechoOutputDTO;
import ar.edu.utn.frba.dds.services.IHechosService;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/hechos")
public class HechosController {

    private final IHechosService hechosService;

    public HechosController(IHechosService hechosService) {
        this.hechosService = hechosService;
    }

    @GetMapping
    public List<HechoOutputDTO> obtenerHechos(@ModelAttribute FiltroHechoDTO filtro) {
        return hechosService.buscarTodos(filtro.getCsvPath());
    }

    @GetMapping("/{id}")
    public HechoOutputDTO obtenerPorId(@PathVariable Long id) {
        return hechosService.buscarPorId(id);
    }

    @PostMapping("/eliminar")
    public Boolean eliminar(@RequestParam String titulo, @RequestParam String path) {
        return hechosService.eliminar(titulo, path);
    }

    @GetMapping("/hechos/nuevos")
    public List<HechoOutputDTO> obtenerSoloNuevos(@RequestParam String csvPath) {
        return hechosService.obtenerNuevosHechos(csvPath);
    }
}
