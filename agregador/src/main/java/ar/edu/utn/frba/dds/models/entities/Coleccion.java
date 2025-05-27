package ar.edu.utn.frba.dds.models.entities;

import ar.edu.utn.frba.dds.models.entities.criterio.Criterio;
import ar.edu.utn.frba.dds.models.entities.Fuente;
import ar.edu.utn.frba.dds.models.entities.hecho.Hecho;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Coleccion {
    private Long ID;
    private List<Criterio> criterios;
    private String titulo;
    private String descripcion;
    private List<Fuente> fuentes;
    private List<Hecho> listaHechos;

    public Coleccion(String titulo, String descripcion,  List<Fuente> fuentes) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.fuentes = fuentes;
        this.listaHechos = new ArrayList<>();
        this.criterios = new ArrayList<>();
    }


    public void agregarCriterio(Criterio criterio) {
        this.criterios.add(criterio);
    }


    public void cargarHechos(List<Hecho> listaHechosObtenidaFuentes) {
        listaHechos.clear();
        if(this.criterios == null) {
            listaHechos.addAll(listaHechosObtenidaFuentes);
        } else{
                for (Hecho hecho : listaHechosObtenidaFuentes) {
                    if (criterios.stream().allMatch(c -> c.evaluarPertenencia(hecho)) && !hecho.estaEliminado()) {
                        listaHechos.add(hecho);
                    }
                }
        }
    }


    public List<Hecho> getHechos() {
        return listaHechos;
    }

    public void agregarHecho(Hecho hecho) {
        if (!hecho.estaEliminado()) {
            this.listaHechos.add(hecho);
        }
    }
}
