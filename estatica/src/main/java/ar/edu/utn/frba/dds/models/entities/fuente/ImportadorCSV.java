package ar.edu.utn.frba.dds.models.entities.fuente;
import ar.edu.utn.frba.dds.models.entities.hecho.Categoria;
import ar.edu.utn.frba.dds.models.entities.hecho.FuenteMetaData;
import ar.edu.utn.frba.dds.models.entities.hecho.Hecho;
import ar.edu.utn.frba.dds.models.entities.hecho.Ubicacion;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;


import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ImportadorCSV implements Fuente {

    private String CSVPath;

    public ImportadorCSV() {
    }

    public ImportadorCSV(String CSVPath) {
        this.CSVPath = CSVPath;
    }

    public void setCSVPath(String CSVPath) {
        this.CSVPath = CSVPath;
    }

    @Override
    public List<Hecho> obtenerHechos() {
        List<Hecho> hechos = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        try (CSVReader reader = new CSVReader(new FileReader(CSVPath))) {
            String[] fila;
            boolean primera = true;

            while ((fila = reader.readNext()) != null) {
                if (primera) {
                    primera = false;
                    continue;
                }

                int offset = fila.length >= 7 ? 1 : 0;

                long id = offset == 1 ? Long.parseLong(fila[0]) : 0;

                String titulo = fila[offset];
                String descripcion = fila[offset + 1];
                String categoria = fila[offset + 2];
                double latitud = Double.parseDouble(fila[offset + 3]);
                double longitud = Double.parseDouble(fila[offset + 4]);
                LocalDate fecha = LocalDate.parse(fila[offset + 5], formatter);

                FuenteMetaData fuenteMeta = new FuenteMetaData("Importador CSV", TipoDeFuente.ESTATICA);

                Hecho hecho = new Hecho(
                        titulo,
                        descripcion,
                        new Categoria(categoria),
                        fecha,
                        fuenteMeta,
                        new Ubicacion(latitud, longitud)
                );
                hecho.setId(id);

                if (fila.length > offset + 6) {
                    boolean eliminado = Boolean.parseBoolean(fila[offset + 6]);
                    hecho.setEliminado(eliminado);
                } else {
                    hecho.setEliminado(false);
                }

                hechos.add(hecho);
            }

        } catch (IOException | CsvValidationException e) {
            System.err.println("Error al leer el archivo CSV: " + e.getMessage());
        }

        return hechos;
    }
}
