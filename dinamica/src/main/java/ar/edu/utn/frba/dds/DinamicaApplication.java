package ar.edu.utn.frba.dds;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import ar.edu.utn.frba.dds.models.repositories.HechoRepository;
import ar.edu.utn.frba.dds.models.repositories.SolicitudEliminacionRepository;
import ar.edu.utn.frba.dds.models.repositories.AdministradorRepository;
import ar.edu.utn.frba.dds.models.repositories.InMemoryHechoRepository;
import ar.edu.utn.frba.dds.models.repositories.InMemorySolicitudEliminacionRepository;
import ar.edu.utn.frba.dds.models.repositories.InMemoryAdministradorRepository;
import ar.edu.utn.frba.dds.models.services.DetectorDeSpam;
import ar.edu.utn.frba.dds.models.services.GestionMultimediaService;
import ar.edu.utn.frba.dds.models.services.DummyDetectorDeSpamImpl;
import ar.edu.utn.frba.dds.models.entities.Administrador;


@SpringBootApplication
public class DinamicaApplication {

    public static void main(String[] args) {
        SpringApplication.run(DinamicaApplication.class, args);
    }

    @Bean
    public HechoRepository hechoRepository() {
        return new InMemoryHechoRepository();
    }

    @Bean
    public SolicitudEliminacionRepository solicitudEliminacionRepository() {
        return new InMemorySolicitudEliminacionRepository();
    }

    @Bean
    public AdministradorRepository administradorRepository() {
        InMemoryAdministradorRepository repo = new InMemoryAdministradorRepository();
        repo.agregar(new Administrador("admin-sistema-default", "Administrador del Sistema"));
        repo.agregar(new Administrador("admin-spam-detector", "Sistema AntiSpam"));
        return repo;
    }

    @Bean
    public ar.edu.utn.frba.dds.services.DummyDetectorDeSpamImpl detectorDeSpam() {
        return new DummyDetectorDeSpamImpl();
    }

    @Bean
    public GestionMultimediaService gestionMultimediaService() {
        return new GestionMultimediaService();
    }
}