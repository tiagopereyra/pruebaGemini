package ar.edu.utn.frba.dds.models.services;

import ar.edu.utn.frba.dds.models.DTOs.PeticionRevisionAdminDTO;
import ar.edu.utn.frba.dds.models.entities.*;
import ar.edu.utn.frba.dds.models.repositories.HechoRepository;
import ar.edu.utn.frba.dds.models.repositories.SolicitudEliminacionRepository;
import ar.edu.utn.frba.dds.models.repositories.AdministradorRepository; // Nuevo
import ar.edu.utn.frba.dds.models.services.DetectorDeSpam;

import java.util.Optional;

public class AdministracionFuentesService {

    private final HechoRepository hechoRepository;
    private final SolicitudEliminacionRepository solicitudRepository;
    private final DetectorDeSpam detectorDeSpam;
    private final AdministradorRepository administradorRepository; // Nuevo

    public AdministracionFuentesService(HechoRepository hechoRepository,
                                        SolicitudEliminacionRepository solicitudRepository,
                                        DetectorDeSpam detectorDeSpam,
                                        AdministradorRepository administradorRepository) { // Nuevo
        this.hechoRepository = hechoRepository;
        this.solicitudRepository = solicitudRepository;
        this.detectorDeSpam = detectorDeSpam;
        this.administradorRepository = administradorRepository; // Nuevo
    }

    /**
     * Permite a un administrador revisar un hecho subido o modificado.
     * Puede aceptar, aceptar con sugerencias o rechazar.
     */
    public RevisionHecho revisarHecho(PeticionRevisionAdminDTO peticion) {
        Optional<Hecho> hechoOptional = hechoRepository.buscarPorId(peticion.getIdHecho());
        if (!hechoOptional.isPresent()) {
            throw new IllegalArgumentException("Hecho con ID " + peticion.getIdHecho() + " no encontrado.");
        }
        Hecho hechoARevisar = hechoOptional.get();

        Optional<Administrador> adminOptional = administradorRepository.buscarPorId(peticion.getIdAdministrador());
        if (!adminOptional.isPresent()) {
            throw new IllegalArgumentException("Administrador con ID " + peticion.getIdAdministrador() + " no encontrado.");
        }
        Administrador administrador = adminOptional.get();

        RevisionHecho revision = hechoARevisar.getRevision();
        if (revision == null) {
            // Esto no debería pasar si subirHecho siempre crea una revisión.
            // Pero por si acaso:
            revision = new RevisionHecho();
            hechoARevisar.setRevision(revision);
        }

        switch (peticion.getDecision()) {
            case APROBADO:
                revision.aprobar(administrador);
                // Lógica adicional si un hecho aprobado debe hacerse visible, etc.
                // hechoARevisar.setEsVisible(true); // Si tienes un campo así
                break;
            case APROBADO_CON_SUGERENCIAS:
                revision.aprobarConSugerencias(administrador, peticion.getSugerencias());
                // hechoARevisar.setEsVisible(true);
                break;
            case RECHAZADO:
                revision.rechazar(administrador);
                // Si se rechaza, el hecho podría marcarse como no visible o incluso eliminarse lógicamente.
                // hechoARevisar.setEsVisible(false);
                // o hechoARevisar.marcarEliminado(); // Depende de la política
                break;
            default:
                throw new IllegalArgumentException("Decisión de revisión no válida: " + peticion.getDecision());
        }

        hechoRepository.guardar(hechoARevisar); // Guardar el hecho con su revisión actualizada
        return revision;
    }

    /**
     * Permite a un administrador aceptar o rechazar una solicitud de eliminación de un hecho.
     */
    public SolicitudEliminacion evaluarSolicitudEliminacion(String idSolicitud, String idAdministrador, boolean aceptar) {
        Optional<SolicitudEliminacion> solicitudOptional = solicitudRepository.buscarPorId(idSolicitud);
        if (!solicitudOptional.isPresent()) {
            throw new IllegalArgumentException("Solicitud de eliminación con ID " + idSolicitud + " no encontrada.");
        }
        SolicitudEliminacion solicitud = solicitudOptional.get();

        Optional<Administrador> adminOptional = administradorRepository.buscarPorId(idAdministrador);
        if (!adminOptional.isPresent()) {
            throw new IllegalArgumentException("Administrador con ID " + idAdministrador + " no encontrado.");
        }
        Administrador administrador = adminOptional.get();

        if (aceptar) {
            solicitud.aceptarSolicitud(administrador); // Esto ya marca el hecho como eliminado
            // Si el hecho necesita ser guardado explícitamente después de marcarEliminado():
            if (solicitud.getHecho() != null) {
                hechoRepository.guardar(solicitud.getHecho());
            }
        } else {
            solicitud.rechazarSolicitud(administrador);
        }
        return solicitudRepository.guardar(solicitud);
    }

    /**
     * Procesa una solicitud de eliminación para detectar spam y rechazarla automáticamente si es necesario.
     */
    public SolicitudEliminacion procesarSolicitudParaSpam(String idSolicitud, String idAdminSpamSystem) {
        Optional<SolicitudEliminacion> solicitudOptional = solicitudRepository.buscarPorId(idSolicitud);
        if (!solicitudOptional.isPresent()) {
            throw new IllegalArgumentException("Solicitud de eliminación con ID " + idSolicitud + " no encontrada.");
        }
        SolicitudEliminacion solicitud = solicitudOptional.get();

        Optional<Administrador> adminSystemOptional = administradorRepository.buscarPorId(idAdminSpamSystem);
        if (!adminSystemOptional.isPresent()) {
            throw new IllegalArgumentException("Administrador de sistema para SPAM con ID " + idAdminSpamSystem + " no encontrado.");
        }
        Administrador adminSpam = adminSystemOptional.get();

        if (solicitud.getEstado() == EstadoSolicitud.PENDIENTE) {
            if (detectorDeSpam.esSpam(solicitud.getMotivo())) { // Asegúrate que SolicitudEliminacion tiene getMotivo()
                solicitud.rechazarPorSpam(adminSpam); // Nuevo método en SolicitudEliminacion
                solicitudRepository.guardar(solicitud);
            }
        }
        return solicitud;
    }
}