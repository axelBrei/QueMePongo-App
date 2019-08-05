package dds.frba.utn.quemepongo.Model;

import android.os.AsyncTask;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import dds.frba.utn.quemepongo.QueMePongo;

public class Evento {
    private String nombre;
    private Date fecha;
    private int hora;
    private int posicion;
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

    public Atuendo obtenerAtuendos(Guardarropa guardarropa){

        Atuendo Atu = guardarropa.generarAtuendo();
        return Atu;
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
