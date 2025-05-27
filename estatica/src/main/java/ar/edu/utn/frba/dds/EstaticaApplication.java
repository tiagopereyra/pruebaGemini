package ar.edu.utn.frba.dds;

import ar.edu.utn.frba.dds.models.entities.fuente.ImportadorCSV;
import ar.edu.utn.frba.dds.models.repositories.IHechosRepository;
import ar.edu.utn.frba.dds.models.repositories.impl.HechosRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class EstaticaApplication {

    public static void main(String[] args) {
        SpringApplication.run(EstaticaApplication.class, args);
    }

    @Bean
    public IHechosRepository hechosRepository() {
        return new HechosRepository();
    }

    @Bean
    public ImportadorCSV importadorCSV() {
        return new ImportadorCSV();
    }
}
