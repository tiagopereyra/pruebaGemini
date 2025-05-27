package ar.edu.utn.frba.dds.services.impl;

import ar.edu.utn.frba.dds.models.dtos.output.CategoriaDTO;
import ar.edu.utn.frba.dds.models.dtos.output.HechoOutputDTO;
import ar.edu.utn.frba.dds.models.dtos.output.UbicacionDTO;
import ar.edu.utn.frba.dds.models.entities.fuente.ImportadorCSV;
import ar.edu.utn.frba.dds.models.entities.hecho.Hecho;
import ar.edu.utn.frba.dds.models.repositories.IHechosRepository;
import ar.edu.utn.frba.dds.services.IHechosService;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

@Service
public class HechosService implements IHechosService {

    private final IHechosRepository hechosRepository;
    private final ImportadorCSV lectorCSV;
    private final Set<Long> idsEntregados = new HashSet<>();

    public HechosService(IHechosRepository hechosRepository, ImportadorCSV lectorCSV) {
        this.hechosRepository = hechosRepository;
        this.lectorCSV = lectorCSV;
    }


    @Override
    public List<HechoOutputDTO> obtenerNuevosHechos(String csvPath) {
        lectorCSV.setCSVPath(csvPath);
        List<Hecho> hechos = lectorCSV.obtenerHechos();

        List<HechoOutputDTO> nuevos = new ArrayList<>();

        for (Hecho hecho : hechos) {
            if (!idsEntregados.contains(hecho.getId()) && !hecho.getEliminado()) {
                idsEntregados.add((Long) hecho.getId());
                nuevos.add(hechoOutputDTO(hecho));
            }
        }

        return nuevos;
    }

    @Override
    public List<HechoOutputDTO> buscarTodos(String csvPath) {
        lectorCSV.setCSVPath(csvPath);
        List<Hecho> hechos = lectorCSV.obtenerHechos();
        hechos.forEach(hechosRepository::guardar);
        return hechos.stream().map(this::hechoOutputDTO).toList();
    }

    @Override
    public HechoOutputDTO buscarPorId(Long id) {
        Hecho hecho = hechosRepository.findById(id);
        return hecho != null ? hechoOutputDTO(hecho) : null;
    }

    @Override
    public Boolean eliminar(String titulo, String csvPath) {
        lectorCSV.setCSVPath(csvPath);
        List<Hecho> hechos = lectorCSV.obtenerHechos();

        for (Hecho hecho : hechos) {
            if (hecho.getTitulo().equalsIgnoreCase(titulo)) {
                hecho.setEliminado(true);
                return true;
            }
        }
        return false;
    }

    @Override
    public Boolean agregarFuente(String csvPath) {
        File archivo = new File(csvPath);
        File archivoTemporal = new File(csvPath + ".tmp");

        try (
                CSVReader reader = new CSVReader(new FileReader(archivo));
                CSVWriter writer = new CSVWriter(new FileWriter(archivoTemporal))
        ) {
            String[] cabecera = reader.readNext();

            if (cabecera == null) return false;

            boolean tieneColumnaEliminado = Arrays.stream(cabecera)
                    .anyMatch(col -> col.equalsIgnoreCase("eliminado"));

            if (tieneColumnaEliminado) return false;

            String[] nuevaCabecera = Arrays.copyOf(cabecera, cabecera.length + 1);
            nuevaCabecera[nuevaCabecera.length - 1] = "eliminado";
            writer.writeNext(nuevaCabecera);

            String[] fila;
            while ((fila = reader.readNext()) != null) {
                String[] nuevaFila = Arrays.copyOf(fila, fila.length + 1);
                nuevaFila[nuevaFila.length - 1] = "false";
                writer.writeNext(nuevaFila);
            }

            writer.flush();


            if (!archivo.delete() || !archivoTemporal.renameTo(archivo)) {
                throw new IOException("No se pudo reemplazar el archivo original.");
            }

            return true;

        } catch (IOException | CsvValidationException e) {
            System.err.println("Error al agregar columna 'eliminado': " + e.getMessage());
            return false;
        }
    }

    @Override
    public Boolean agregarColumnaId(String csvPath) {
        File original = new File(csvPath);
        File temporal = new File(csvPath + ".tmp");

        try (
                CSVReader reader = new CSVReader(new FileReader(original));
                CSVWriter writer = new CSVWriter(new FileWriter(temporal))
        ) {
            String[] encabezado = reader.readNext();
            if (encabezado == null) return false;

            boolean tieneId = Arrays.stream(encabezado)
                    .anyMatch(col -> col.equalsIgnoreCase("id"));

            if (tieneId) return false; // Ya tiene columna id


            String[] nuevoEncabezado = new String[encabezado.length + 1];
            nuevoEncabezado[0] = "id";
            System.arraycopy(encabezado, 0, nuevoEncabezado, 1, encabezado.length);
            writer.writeNext(nuevoEncabezado);

            String[] fila;
            long id = 1;

            while ((fila = reader.readNext()) != null) {
                String[] nuevaFila = new String[fila.length + 1];
                nuevaFila[0] = String.valueOf(id++);
                System.arraycopy(fila, 0, nuevaFila, 1, fila.length);
                writer.writeNext(nuevaFila);
            }

            writer.flush();

            if (!original.delete() || !temporal.renameTo(original)) {
                throw new IOException("No se pudo reemplazar el archivo original.");
            }

            return true;

        } catch (IOException | CsvValidationException e) {
            System.err.println("Error al agregar columna 'id': " + e.getMessage());
            return false;
        }
    }



    private HechoOutputDTO hechoOutputDTO(Hecho hecho){
        HechoOutputDTO dto = new HechoOutputDTO();
        dto.setTitulo(hecho.getTitulo());
        dto.setId(hecho.getId());
        dto.setDescripcion(hecho.getDescripcion());
        dto.setCategoria(new CategoriaDTO(hecho.getCategoria().getNombre()));
        dto.setFechaAcontecimiento(hecho.getFechaAcontecimiento());
        UbicacionDTO ubicacionDTO = new UbicacionDTO();
        ubicacionDTO.setLatitud(hecho.getUbicacion().getLatitud());
        ubicacionDTO.setLongitud(hecho.getUbicacion().getLongitud());
        dto.setUbicacion(ubicacionDTO);
        return dto;
    }
}
