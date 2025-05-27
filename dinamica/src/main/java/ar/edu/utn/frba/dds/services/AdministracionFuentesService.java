package ar.edu.utn.frba.dds.models.services;

import ar.edu.utn.frba.dds.models.DTOs.input.RevisionAdminDTO;
import ar.edu.utn.frba.dds.models.DTOs.input.SolicitudEliminacionCreacionDTO;
import ar.edu.utn.frba.dds.models.entities.Administrador;
import ar.edu.utn.frba.dds.models.entities.Hecho;
import ar.edu.utn.frba.dds.models.entities.RevisionHecho;
import ar.edu.utn.frba.dds.models.entities.SolicitudEliminacion;
import ar.edu.utn.frba.dds.models.entities.EstadoSolicitud;
import ar.edu.utn.frba.dds.models.repositories.AdministradorRepository;
import ar.edu.utn.frba.dds.models.repositories.HechoRepository;
import ar.edu.utn.frba.dds.models.repositories.SolicitudEliminacionRepository;
import ar.edu.utn.frba.dds.services.DetectorDeSpam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
class AdministracionHechosService {

    private final HechoRepository hechoRepository;
    private final SolicitudEliminacionRepository solicitudRepository;
    private final AdministradorRepository administradorRepository;
    private final DetectorDeSpam detectorDeSpam;

    @Autowired
    public AdministracionHechosService(HechoRepository hechoRepository,
                                       SolicitudEliminacionRepository solicitudRepository,
                                       AdministradorRepository administradorRepository,
                                       @Qualifier("dummyDetectorDeSpam") DetectorDeSpam detectorDeSpam) {
        this.hechoRepository = hechoRepository;
        this.solicitudRepository = solicitudRepository;
        this.administradorRepository = administradorRepository;
        this.detectorDeSpam = detectorDeSpam;
    }

    public RevisionHecho revisarHecho(RevisionAdminDTO dto) {
        Hecho hechoARevisar = hechoRepository.buscarPorId(dto.getIdHecho())
                .orElseThrow(() -> new IllegalArgumentException("Hecho con ID " + dto.getIdHecho() + " no encontrado."));

        Administrador administrador = administradorRepository.buscarPorId(dto.getIdAdministrador())
                .orElseThrow(() -> new IllegalArgumentException("Administrador con ID " + dto.getIdAdministrador() + " no encontrado."));

        RevisionHecho revision = hechoARevisar.getRevision();
        if (revision == null) {
            revision = new RevisionHecho();
            hechoARevisar.setRevision(revision);
        }

        switch (dto.getDecision()) {
            case APROBADO:
                revision.aprobar(administrador);
                break;
            case APROBADO_CON_SUGERENCIAS:
                revision.aprobarConSugerencias(administrador, dto.getSugerencias());
                break;
            case RECHAZADO:
                revision.rechazar(administrador);
                break;
            default:
                throw new IllegalArgumentException("Decisión de revisión no válida: " + dto.getDecision());
        }
        hechoRepository.guardar(hechoARevisar);
        return revision;
    }

    public SolicitudEliminacion crearSolicitudEliminacion(SolicitudEliminacionCreacionDTO dto, String idAdminSpamSystem) {
        Hecho hecho = hechoRepository.buscarPorId(dto.getIdHechoAEliminar())
                .orElseThrow(() -> new IllegalArgumentException("Hecho a eliminar no encontrado con ID: " + dto.getIdHechoAEliminar()));

        if (dto.getMotivo() == null || dto.getMotivo().length() < 500) {
            throw new IllegalArgumentException("El motivo de la solicitud de eliminación debe tener al menos 500 caracteres.");
        }

        SolicitudEliminacion solicitud = new SolicitudEliminacion(dto.getMotivo(), hecho);

        if (detectorDeSpam.esSpam(solicitud.getMotivo())) {
            Administrador adminSpam = administradorRepository.buscarPorId(idAdminSpamSystem)
                    .orElseThrow(() -> new IllegalStateException("Administrador de sistema para SPAM no configurado o no encontrado."));
            solicitud.rechazarPorSpam(adminSpam);
        }
        return solicitudRepository.guardar(solicitud);
    }


    public SolicitudEliminacion evaluarSolicitudEliminacion(String idSolicitud, String idAdministrador, boolean aceptar) {
        SolicitudEliminacion solicitud = solicitudRepository.buscarPorId(idSolicitud)
                .orElseThrow(() -> new IllegalArgumentException("Solicitud de eliminación con ID " + idSolicitud + " no encontrada."));

        Administrador administrador = administradorRepository.buscarPorId(idAdministrador)
                .orElseThrow(() -> new IllegalArgumentException("Administrador con ID " + idAdministrador + " no encontrado."));

        if (solicitud.getEstado() != EstadoSolicitud.PENDIENTE) {
            throw new IllegalStateException("La solicitud ya fue procesada y su estado es: " + solicitud.getEstado());
        }

        if (aceptar) {
            solicitud.aceptarSolicitud(administrador);
            if (solicitud.getHecho() != null) {
                hechoRepository.guardar(solicitud.getHecho());
            }
        } else {
            solicitud.rechazarSolicitud(administrador);
        }
        return solicitudRepository.guardar(solicitud);
    }
}
