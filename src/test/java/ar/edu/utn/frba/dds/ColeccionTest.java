package ar.edu.utn.frba.dds;
import static org.junit.jupiter.api.Assertions.*;

import ar.edu.utn.frba.dds.models.entities.*;
import ar.edu.utn.frba.dds.models.entities.criterio.Criterio;
import ar.edu.utn.frba.dds.models.entities.criterio.CriterioPorCategoria;
import ar.edu.utn.frba.dds.models.entities.criterio.CriterioPorFecha;
import ar.edu.utn.frba.dds.models.entities.fuente.Fuente;
import ar.edu.utn.frba.dds.models.entities.fuente.ImportadorCSV;
import ar.edu.utn.frba.dds.models.entities.hecho.Categoria;
import ar.edu.utn.frba.dds.models.entities.hecho.Etiqueta;
import ar.edu.utn.frba.dds.models.entities.hecho.Hecho;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

class ColeccionTest {

    private Coleccion coleccion;
    String path = "C:\\Users\\Santiago\\Documents\\facultad\\TERCERO\\DISENIO\\2025_Dise-oDeSistemas\\2025-tpa-ma-ma-grupo-21\\eventos_emergencia.csv";

    @BeforeEach
    public void setUp() {
        Fuente fuente = new ImportadorCSV(path);
        coleccion = new Coleccion("Coleccion prueba", "Esto es una prueba", fuente);
        coleccion.cargarHechos(); // TODO Esto debería llenar la colección con hechos si el CSV tiene datos
    }

    @Test // 1.1
    public void sePuedenObtenerHechosDesdeLaColeccion() {
        List<Hecho> hechos = coleccion.getHechos();

        assertEquals(5, hechos.size(), "La cantidad de hechos deberia de ser igual a 5");
    }

    @Test // 1.2
    public void aplicarCriteriosDePertenencia(){

        LocalDate fechaInicio = LocalDate.of(2000, 1, 1);
        LocalDate fechaFinal = LocalDate.of(2010, 1, 1);
        Criterio criterioFecha = new CriterioPorFecha(fechaInicio, fechaFinal);
        Categoria categoria = new Categoria("Caída de aeronave");
        Criterio criterioCategoria = new CriterioPorCategoria(categoria);

        coleccion.agregarCriterio(criterioFecha);
        coleccion.agregarCriterio(criterioCategoria);

        coleccion.cargarHechos();

        List<Hecho> hechosFiltrados = coleccion.getHechos();

        assertEquals(2, hechosFiltrados.size(), "La cantidad de hechos tendria que ser 2");

    }


    @Test // 1.3
    public void filtrarHechos(){
        Categoria categoria = new Categoria("Accidente con maquinaria industrial");
        Filtrador filtrador = new Filtrador();
        //filtrador.setTitulo("Caída de aeronave impacta en Olavarría");
        filtrador.setCategoria(categoria);
        List<Hecho> hechos = coleccion.getHechos();
        assertEquals(1, filtrador.filtrar(hechos).size(), "Ningun hecho deberia cumplir ambos filtros");
    }


    @Test // 1.4
    public void etiquetarHechos(){

        Filtrador filtrador = new Filtrador();
        filtrador.setTitulo("Caída de aeronave impacta en Olavarría");
        List<Hecho>hechos = filtrador.filtrar(coleccion.getHechos());

        Hecho hechoCorrespondiente = hechos.get(0);

        Etiqueta etiquetaOlavarria = new Etiqueta("Olavarria");
        Etiqueta etiquetaGrave = new Etiqueta("Grave");

        hechoCorrespondiente.agregarEtiqueta(etiquetaOlavarria);
        hechoCorrespondiente.agregarEtiqueta(etiquetaGrave);

        List<Etiqueta>etiquetas = hechoCorrespondiente.getEtiquetas();
        assertEquals(2, etiquetas.size(), "La cantidad de etiquetas del hecho deberian ser 2");
        assertTrue(etiquetas.contains(etiquetaOlavarria), "Debería contener la etiqueta 'Olavarría'");
        assertTrue(etiquetas.contains(etiquetaGrave), "Debería contener la etiqueta 'Grave'");
    }

}