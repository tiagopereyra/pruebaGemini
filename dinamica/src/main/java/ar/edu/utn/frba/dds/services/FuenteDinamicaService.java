package ar.edu.utn.frba.dds.services;

import ar.edu.utn.frba.dds.models.DTOs.input.HechoCreacionDTO;
import ar.edu.utn.frba.dds.models.DTOs.input.HechoModificacionDTO;
import ar.edu.utn.frba.dds.models.entities.*;
import ar.edu.utn.frba.dds.models.entities.EstadoRevision;
import ar.edu.utn.frba.dds.models.repositories.ContribuyenteRepository;
import ar.edu.utn.frba.dds.models.repositories.HechoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;


import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class FuenteDinamicaService {

    private final HechoRepository hechoRepository;
    private final GestionMultimediaService gestionMultimediaService;
    private final ContribuyenteRepository contribuyenteRepository;

    @Autowired
    public FuenteDinamicaService(HechoRepository hechoRepository,
                                 GestionMultimediaService gestionMultimediaService,
                                 ContribuyenteRepository contribuyenteRepository) {
        this.hechoRepository = hechoRepository;
        this.gestionMultimediaService = gestionMultimediaService;
        this.contribuyenteRepository = contribuyenteRepository;
    }

    public Hecho subirHecho(HechoCreacionDTO dto) {
        if (dto == null) {
            throw new IllegalArgumentException("La petición para crear el hecho no puede ser nula.");
        }

        Ubicacion ubicacion = new Ubicacion(dto.getLatitudUbicacion(), dto.getLongitudUbicacion());
        Categoria categoria = new Categoria(dto.getNombreCategoria());
        Multimedia contenidoMultimedia = gestionMultimediaService.procesarYGuardar(
                dto.getTipoMultimedia(),
                dto.getDatosMultimedia()
        );

        Contribuyente contribuyente = null;
        if (StringUtils.hasText(dto.getIdContribuyente())) {
            contribuyente = contribuyenteRepository.buscarPorId(dto.getIdContribuyente())
                    .orElseThrow(() -> new IllegalArgumentException("Contribuyente registrado no encontrado con ID: " + dto.getIdContribuyente()));
        } else if (StringUtils.hasText(dto.getNombreContribuyente())) {
            contribuyente = new Contribuyente(
                    UUID.randomUUID().toString(),
                    dto.getNombreContribuyente(),
                    dto.getApellidoContribuyente(),
                    dto.getFechaNacimientoContribuyente()
            );
            contribuyenteRepository.guardar(contribuyente);
        }


        TipoDeFuente origen = (contribuyente != null) ? TipoDeFuente.CONTRIBUYENTE : TipoDeFuente.MANUAL;

        Hecho nuevoHecho = new Hecho(
                dto.getTitulo(),
                dto.getDescripcion(),
                categoria,
                dto.getFechaAcontecimiento(),
                contribuyente,
                origen,
                ubicacion,
                contenidoMultimedia
        );

        return hechoRepository.guardar(nuevoHecho);
    }

    public Hecho modificarHecho(String idHecho, HechoModificacionDTO dto, String idContribuyenteEditor) {
        if (idHecho == null || dto == null || idContribuyenteEditor == null) {
            throw new IllegalArgumentException("ID del hecho, petición de modificación e ID del contribuyente editor no pueden ser nulos.");
        }

        Hecho hechoAModificar = hechoRepository.buscarPorId(idHecho)
                .orElseThrow(() -> new IllegalArgumentException("Hecho con ID " + idHecho + " no encontrado."));

        if (hechoAModificar.getContribuyente() == null) {
            throw new SecurityException("Este hecho fue subido anónimamente y no puede ser modificado.");
        }

        if (!hechoAModificar.getContribuyente().getId().equals(idContribuyenteEditor)) {
            throw new SecurityException("No tiene permisos para modificar este hecho. No es el contribuyente original.");
        }

        long diasDesdeCarga = ChronoUnit.DAYS.between(hechoAModificar.getFechaCarga(), LocalDate.now());
        if (diasDesdeCarga > 7) {
            throw new SecurityException("El plazo para modificar el hecho (1 semana desde su carga original) ha expirado.");
        }

        hechoAModificar.setTitulo(dto.getTitulo());
        hechoAModificar.setDescripcion(dto.getDescripcion());
        hechoAModificar.setCategoria(new Categoria(dto.getNombreCategoria()));
        hechoAModificar.setFechaAcontecimiento(dto.getFechaAcontecimiento());
        hechoAModificar.setUbicacion(new Ubicacion(dto.getLatitudUbicacion(), dto.getLongitudUbicacion()));
        hechoAModificar.setContenidoMultimedia(
                gestionMultimediaService.procesarYGuardar(dto.getTipoMultimedia(), dto.getDatosMultimedia())
        );

        RevisionHecho revision = hechoAModificar.getRevision();
        if (revision == null) {
            revision = new RevisionHecho();
        }
        revision.setEstado(EstadoRevision.PENDIENTE);
        revision.setAdministrador(null);
        revision.setFechaRevisado(null);
        revision.setSugerencia(null);
        hechoAModificar.setRevision(revision);

        return hechoRepository.guardar(hechoAModificar);
    }

    public List<Hecho> obtenerHechosDeFuenteDinamica(boolean soloAprobados) {
        return hechoRepository.buscarTodos().stream()
                .filter(hecho -> (hecho.getOrigen() == TipoDeFuente.CONTRIBUYENTE || hecho.getOrigen() == TipoDeFuente.MANUAL))
                .filter(hecho -> !hecho.isEliminado())
                .filter(hecho -> !soloAprobados || (hecho.getRevision() != null && (hecho.getRevision().getEstado() == EstadoRevision.APROBADO || hecho.getRevision().getEstado() == EstadoRevision.APROBADO_CON_SUGERENCIAS) ))
                .collect(Collectors.toList());
    }
}