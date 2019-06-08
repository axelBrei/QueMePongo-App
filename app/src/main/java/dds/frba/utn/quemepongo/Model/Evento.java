package dds.frba.utn.quemepongo.Model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Evento {
    private String nombre;
    private Date fecha;
    private int hora;
    private Ubicacion ubicacion;
    private List<Atuendo> sugeridos = new ArrayList<Atuendo>();
    private Atuendo seleccionado;

    public Evento (String nombre,Date fecha, Ubicacion ubicacion){
        this.nombre = nombre;
        this.fecha = fecha;
        this.ubicacion = ubicacion;
    }

    public Ubicacion getUbicacion(){
        return this.ubicacion;
    }

    public void obtenerAtuendos(){

    }

    public Date getDate(){
        return this.fecha;
    }

    public  void rechazar(Atuendo atuendo){
        sugeridos.remove(atuendo);
    }

    public void aceptar(Atuendo atuendo){
        this.seleccionado = atuendo;
    }

}
