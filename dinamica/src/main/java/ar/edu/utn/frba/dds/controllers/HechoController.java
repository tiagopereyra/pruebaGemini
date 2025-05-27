package ar.edu.utn.frba.dds.controllers;

import ar.edu.utn.frba.dds.models.DTOs.input.HechoCreacionDTO;
import ar.edu.utn.frba.dds.models.DTOs.input.HechoModificacionDTO;
import ar.edu.utn.frba.dds.models.DTOs.output.HechoDinamicoDTO;
import ar.edu.utn.frba.dds.models.DTOs.output.UbicacionOutputDTO;
import ar.edu.utn.frba.dds.models.entities.Hecho;
import ar.edu.utn.frba.dds.services.FuenteDinamicaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;


import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/dinamica/hechos")
public class HechoController {

    private final FuenteDinamicaService fuenteDinamicaService;

    @Autowired
    public HechoController(FuenteDinamicaService fuenteDinamicaService) {
        this.fuenteDinamicaService = fuenteDinamicaService;
    }

    @PostMapping
    public ResponseEntity<HechoDinamicoDTO> subirHecho(@RequestBody HechoCreacionDTO dto) {
        try {
            Hecho hechoGuardado = fuenteDinamicaService.subirHecho(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(mapToHechoDinamicoDTO(hechoGuardado));
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al subir el hecho", e);
        }
    }

    @PutMapping("/{idHecho}")
    public ResponseEntity<HechoDinamicoDTO> modificarHecho(
            @PathVariable String idHecho,
            @RequestBody HechoModificacionDTO dto,
            @RequestHeader("X-Contribuyente-Id") String idContribuyenteEditor) {
        try {
            Hecho hechoModificado = fuenteDinamicaService.modificarHecho(idHecho, dto, idContribuyenteEditor);
            return ResponseEntity.ok(mapToHechoDinamicoDTO(hechoModificado));
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        } catch (SecurityException e) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, e.getMessage(), e);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al modificar el hecho", e);
        }
    }

    @GetMapping
    public ResponseEntity<List<HechoDinamicoDTO>> obtenerHechosDinamicos(@RequestParam(defaultValue = "false") boolean soloAprobados) {
        List<Hecho> hechos = fuenteDinamicaService.obtenerHechosDeFuenteDinamica(soloAprobados);
        List<HechoDinamicoDTO> dtos = hechos.stream().map(this::mapToHechoDinamicoDTO).collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }


    private HechoDinamicoDTO mapToHechoDinamicoDTO(Hecho hecho) {
        if (hecho == null) return null;
        HechoDinamicoDTO dto = new HechoDinamicoDTO();
        dto.setIdentificador(hecho.getIdentificador());
        dto.setTitulo(hecho.getTitulo());
        dto.setDescripcion(hecho.getDescripcion());
        if (hecho.getCategoria() != null) {
            dto.setNombreCategoria(hecho.getCategoria().getNombre());
        }
        dto.setFechaAcontecimiento(hecho.getFechaAcontecimiento());
        dto.setFechaCarga(hecho.getFechaCarga());

        if (hecho.getUbicacion() != null) {
            UbicacionOutputDTO ubicacionDTO = new UbicacionOutputDTO();
            ubicacionDTO.setLatitud(hecho.getUbicacion().getLatitud());
            ubicacionDTO.setLongitud(hecho.getUbicacion().getLongitud());
            dto.setUbicacion(ubicacionDTO);
        }

        if (hecho.getContenidoMultimedia() != null) {
            dto.setTipoMultimedia(hecho.getContenidoMultimedia().getTipo());
            dto.setReferenciaMultimedia(hecho.getContenidoMultimedia().getReferencia());
        }

        if (hecho.getContribuyente() != null) {
            dto.setNombreContribuyente(hecho.getContribuyente().getNombre());
        } else {
            dto.setNombreContribuyente("Anónimo");
        }

        if (hecho.getRevision() != null) {
            dto.setEstadoRevision(hecho.getRevision().getEstado());
        }
        dto.setOrigen(hecho.getOrigen());
        dto.setEliminado(hecho.isEliminado());
        return dto;
    }
}
