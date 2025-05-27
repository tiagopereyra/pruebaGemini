package ar.edu.utn.frba.dds.controllers;

import ar.edu.utn.frba.dds.models.DTOs.input.RevisionAdminDTO;
import ar.edu.utn.frba.dds.models.DTOs.input.SolicitudEliminacionCreacionDTO;
import ar.edu.utn.frba.dds.models.entities.RevisionHecho;
import ar.edu.utn.frba.dds.models.entities.SolicitudEliminacion;
import ar.edu.utn.frba.dds.services.AdministracionHechosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;


@RestController
@RequestMapping("/api/dinamica/admin")
public class AdministracionController {

    private final AdministracionHechosService administracionHechosService;
    private final String ID_ADMIN_SPAM_SYSTEM = "admin-spam-detector";

    @Autowired
    public AdministracionController(AdministracionHechosService administracionHechosService) {
        this.administracionHechosService = administracionHechosService;
    }

    @PostMapping("/hechos/revisiones")
    public ResponseEntity<?> revisarHecho(@RequestBody RevisionAdminDTO dto) {
        try {
            RevisionHecho revision = administracionHechosService.revisarHecho(dto);
            return ResponseEntity.ok(revision);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }  catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al revisar el hecho", e);
        }
    }

    @PostMapping("/solicitudes-eliminacion")
    public ResponseEntity<?> crearSolicitudEliminacion(@RequestBody SolicitudEliminacionCreacionDTO dto) {
        try {
            SolicitudEliminacion solicitud = administracionHechosService.crearSolicitudEliminacion(dto, ID_ADMIN_SPAM_SYSTEM);
            return ResponseEntity.status(HttpStatus.CREATED).body(solicitud);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al crear la solicitud de eliminación", e);
        }
    }


    @PutMapping("/solicitudes-eliminacion/{idSolicitud}/evaluar")
    public ResponseEntity<?> evaluarSolicitudEliminacion(
            @PathVariable String idSolicitud,
            @RequestParam String idAdministrador,
            @RequestParam boolean aceptar) {
        try {
            SolicitudEliminacion solicitud = administracionHechosService.evaluarSolicitudEliminacion(idSolicitud, idAdministrador, aceptar);
            return ResponseEntity.ok(solicitud);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        } catch (IllegalStateException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage(), e);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al evaluar la solicitud", e);
        }
    }
}
