package tests.integration;

import ar.edu.utn.frba.dds.models.entities.fuente.ImportadorCSV;
import ar.edu.utn.frba.dds.models.repositories.impl.HechosRepository;
import ar.edu.utn.frba.dds.services.IHechosService;
import ar.edu.utn.frba.dds.services.impl.HechosService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HechosServiceTest {

    private IHechosService hechosService;

    @BeforeEach
    void setUp() {
        ImportadorCSV importador = new ImportadorCSV("src/test/resources/hechos_con_id.csv");
        hechosService = new HechosService(new HechosRepository(), importador);
    }

    @Test
    void testObtenerSoloNuevos() {
        List<?> nuevos1 = hechosService.obtenerNuevosHechos("src/test/resources/hechos_con_id.csv");
        assertEquals(2, nuevos1.size());

        List<?> nuevos2 = hechosService.obtenerNuevosHechos("src/test/resources/hechos_con_id.csv");
        assertEquals(0, nuevos2.size());
    }
}
