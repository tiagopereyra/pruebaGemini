package ar.edu.utn.frba.dds;
import java.util.List;

import ar.edu.utn.frba.dds.models.entities.hecho.Hecho;
import ar.edu.utn.frba.dds.models.entities.fuente.ImportadorCSV;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FuenteEstaticaCSVTest {

    @Test // 2
    public void leerArchivoCSV(){

        String path = "C:\\Users\\Santiago\\Documents\\facultad\\TERCERO\\DISENIO\\2025_Dise-oDeSistemas\\2025-tpa-ma-ma-grupo-21\\eventos_emergencia.csv";

        ImportadorCSV fuente = new ImportadorCSV(path);

        List<Hecho>hechos = fuente.obtenerHechos();
        assertEquals(5, hechos.size(), "La cantidad de hechos deberia ser igual a 5");

    }

}