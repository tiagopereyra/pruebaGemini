package ar.edu.utn.frba.dds.services.impl;

import ar.edu.utn.frba.dds.models.DTOs.input.ColeccionDTO;
import ar.edu.utn.frba.dds.models.DTOs.input.HechoInputEstaticaDTO;
import ar.edu.utn.frba.dds.models.DTOs.input.HechoResponseEstaticaDTO;
import ar.edu.utn.frba.dds.models.DTOs.output.HechoDTO;
import ar.edu.utn.frba.dds.models.DTOs.output.SolicitudEliminacionDTO;
import ar.edu.utn.frba.dds.models.entities.Coleccion;
import ar.edu.utn.frba.dds.models.entities.hecho.TipoDeFuente;
import ar.edu.utn.frba.dds.models.entities.hecho.Categoria;
import ar.edu.utn.frba.dds.models.entities.hecho.Hecho;
import ar.edu.utn.frba.dds.models.entities.hecho.Ubicacion;
import ar.edu.utn.frba.dds.models.repositories.IColeccionRepositorie;
import ar.edu.utn.frba.dds.services.IColeccionService;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import java.util.stream.Collectors;

import java.util.ArrayList;
import java.util.List;

@Service
public class ColeccionService implements IColeccionService {

    private final IColeccionRepositorie coleccionRepositorie;
    private final WebClient webClient;

    public ColeccionService(IColeccionRepositorie coleccionRepositorie, WebClient webClient) {
        this.coleccionRepositorie = coleccionRepositorie;
        this.webClient = webClient;
    }

    // TODO esto hay que acerlo Mono también
    @Override
    public void actualizarHechos() {
        List<Coleccion> colecciones = this.coleccionRepositorie.findAll();

        colecciones.forEach(coleccion -> {
            coleccion.getFuentes().forEach(fuente -> {
                //if(fuente.getTipo() != TipoDeFuente.MANUAL)
                // TODO hacer actualización de hechos, llamar a obtenerHechosFuente, agregar solo los hechos nuevos
            });
        });
    }

    @Override
    public List<HechoDTO> devolverHechosDeColeccion(String coleccionID) {
        return List.of();
    }

    @Override
    public boolean guardarSolicitudesEliminacion(SolicitudEliminacionDTO solicitud) {
        // TODO hacer el handle de las solicitudes de eliminación
        return false;
    }

    @Override
    public boolean guardarColeccion(ColeccionDTO coleccionDTO) {

        return false;
    }

    public Mono<List<Hecho>> obtenerHechosFuenteEstatica(String uri, String CSVPath){
        return webClient.get()
                .uri(uriBuilder ->
                    uriBuilder
                            .path(uri)
                            .queryParam("path", CSVPath)
                            .build()
                )
                .retrieve()
                .bodyToMono(HechoResponseEstaticaDTO.class)
                .map( response -> {
                    List<Hecho> hechos = new ArrayList<>();
                    List<HechoInputEstaticaDTO> hechosDTOS = response.getHechos();
                    hechosDTOS.forEach(hechoDTO -> {
                        Categoria categoria = new Categoria(hechoDTO.getCategoria().getNombre());
                        Ubicacion ubicacion = new Ubicacion(hechoDTO.getUbicacion().getLatitud(), hechoDTO.getUbicacion().getLongitud());
                        Hecho hecho = new Hecho(
                                hechoDTO.getTitulo(),
                                hechoDTO.getDescripcion(),
                                categoria,
                                hechoDTO.getFechaAcontecimiento(),
                                TipoDeFuente.FUENTE_ESTATICA,
                                ubicacion
                                );
                        hechos.add(hecho);
                    });
                    return hechos;
                });
    }

    public Mono<List<Hecho>> obtenerHechosFuenteDinamica(String uri){
        return webClient.get()
                .uri( uriBuilder -> uriBuilder.path(uri).build())
                .retrieve()
                .bodyToMono();
    }
}
