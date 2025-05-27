package ar.edu.utn.frba.dds.models.entities;


import java.util.ArrayList;
import java.util.List;

public class FuenteDinamica implements Fuente{


    @Override
    public List<Hecho> obtenerHechos(){
        List<Hecho> hechos = new ArrayList<>();


        return hechos;
    }

    public void agregarHecho(Hecho hecho){
        // TODO hacer que se agregue un hecho al repositorio de hechos, esto se hace en un service
    }

    public void modificarHecho(Hecho hecho){

    }
}
