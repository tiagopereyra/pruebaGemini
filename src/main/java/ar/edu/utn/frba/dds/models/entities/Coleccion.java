package ar.edu.utn.frba.dds.models.entities;

import ar.edu.utn.frba.dds.models.entities.criterio.Criterio;
import ar.edu.utn.frba.dds.models.entities.fuente.Fuente;
import ar.edu.utn.frba.dds.models.entities.hecho.Hecho;

import java.util.ArrayList;
import java.util.List;

public class Coleccion {
    private List<Criterio> criterios;
    private String titulo;
    private String descripcion;
    private Fuente fuente;
    private List<Hecho> listaHechos;

    public Coleccion(String titulo, String descripcion, Fuente fuente) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.fuente = fuente;
        this.listaHechos = new ArrayList<>();
        this.criterios = new ArrayList<>();
    }


    public void agregarCriterio(Criterio criterio) {
        this.criterios.add(criterio);
    }

    public void cargarHechos() {
        listaHechos.clear();
        if(this.criterios == null) {
            for (Hecho hecho : fuente.obtenerHechos()){
                listaHechos.add(hecho);
            }
        } else{
                for (Hecho hecho : fuente.obtenerHechos()) {
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
