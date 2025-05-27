package ar.edu.utn.frba.dds.models.entities.fuente;
import ar.edu.utn.frba.dds.models.entities.hecho.TipoDeFuente;
import ar.edu.utn.frba.dds.models.entities.hecho.Ubicacion;
import ar.edu.utn.frba.dds.models.entities.hecho.Categoria;
import ar.edu.utn.frba.dds.models.entities.hecho.Hecho;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;


import java.util.ArrayList;
import java.util.List;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ImportadorCSV implements Fuente {
    private String CSVPath;

    public ImportadorCSV(String CSVPath) {
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

                String titulo = fila[0];
                String descripcion = fila[1];
                String categoria = fila[2];
                double latitud = Double.parseDouble(fila[3]);
                double longitud = Double.parseDouble(fila[4]);
                LocalDate fecha = LocalDate.parse(fila[5], formatter);

                Hecho hecho = new Hecho(titulo, descripcion, new Categoria(categoria), fecha, TipoDeFuente.FUENTE_ESTATICA, new Ubicacion(latitud, longitud));
                hechos.add(hecho);
            }

        } catch (IOException e) {
            System.err.println("Error al leer el archivo CSV: " + e.getMessage());
        } catch (CsvValidationException e) {
            throw new RuntimeException(e);
        }

        return hechos;
    }
}
