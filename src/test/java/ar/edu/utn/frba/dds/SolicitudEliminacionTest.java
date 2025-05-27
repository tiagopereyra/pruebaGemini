
package ar.edu.utn.frba.dds;
import java.util.List;
import java.time.LocalDate;

import ar.edu.utn.frba.dds.models.entities.*;
import ar.edu.utn.frba.dds.models.entities.fuente.ImportadorCSV;
import ar.edu.utn.frba.dds.models.entities.hecho.TipoDeFuente;
import ar.edu.utn.frba.dds.models.entities.hecho.Categoria;
import ar.edu.utn.frba.dds.models.entities.hecho.Hecho;
import ar.edu.utn.frba.dds.models.entities.hecho.Ubicacion;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SolicitudEliminacionTest {

    private SolicitudEliminacion solicitud;
    private Hecho broteSanitario;
    private Coleccion coleccion;
    private Administrador administrador;


    @BeforeEach
    public void setUp() {
        broteSanitario = new Hecho(
                "Brote de enfermedad contagiosa causa estragos en San Lorenzo, Santa Fe",
                "Grave brote de enfermedad contagiosa ocurrió en las inmediaciones de San Lorenzo, Santa Fe. El incidente dejó varios heridos y daños materiales. Se ha declarado estado de emergencia en la región para facilitar la asistencia.",
                new Categoria("Evento sanitario"),
                LocalDate.of(2005, 7, 5),
                TipoDeFuente.FUENTE_ESTATICA,
                new Ubicacion(-32.786098,
                        -60.741543)
        );

        solicitud = new SolicitudEliminacion("El hecho ha sido eliminado del sistema tras realizar un proceso de verificación exhaustivo que determinó inconsistencias importantes en los datos reportados originalmente. Se constató que la información relacionada con la ubicación y la fecha del incidente era incorrecta, y no pudo ser validada con fuentes confiables. Además, medios oficiales han desmentido la ocurrencia del evento, indicando que se trató de un error en la interpretación de reportes preliminares. Mantener el hecho en el sistema podría inducir a conclusiones erróneas o propagar desinformación. Por lo tanto, y con el fin de garantizar la integridad de la base de datos y la calidad de los análisis derivados, se procede a su eliminación definitiva.",
                broteSanitario);

        coleccion = new Coleccion("Coleccion", "nula", new ImportadorCSV(""));

        administrador = new Administrador("Luca");
    }


    @Test // 3
    public void rechazarSolicitud() {

        solicitud.rechazarSolicitud(administrador);

        coleccion.agregarHecho(broteSanitario);

        List<Hecho>hechos = coleccion.getHechos();
        assertEquals(1, hechos.size(), "La cantidad de hechos deberia ser igual a 1");

    }

    @Test
    public void aceptarSolicitud() {

        solicitud.aceptarSolicitud(administrador);

        coleccion.agregarHecho(broteSanitario);

        List<Hecho>hechos = coleccion.getHechos();
        assertEquals(0, hechos.size(), "La cantidad de hechos deberia ser igual a 0");

    }
}