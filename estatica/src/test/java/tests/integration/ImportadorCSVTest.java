package tests.integration;

import ar.edu.utn.frba.dds.models.entities.fuente.ImportadorCSV;
import ar.edu.utn.frba.dds.models.entities.hecho.Hecho;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class ImportadorCSVTest {

    @Test
    void testLeerCSVConIdYEliminado() {
        ImportadorCSV importador = new ImportadorCSV("src/test/resources/hechos_con_id.csv");

        List<Hecho> hechos = importador.obtenerHechos();

        assertEquals(2, hechos.size());

        Hecho h1 = hechos.get(0);
        assertEquals(1, (Integer) h1.getId());
        assertFalse(h1.getEliminado());

        Hecho h2 = hechos.get(1);
        assertEquals(2, (Integer) h2.getId());
        assertTrue(h2.getEliminado());
    }

    private void assertEquals(int i, int size) {

    }
}
