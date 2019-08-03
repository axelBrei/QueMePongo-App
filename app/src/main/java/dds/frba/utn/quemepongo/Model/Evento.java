package dds.frba.utn.quemepongo.Model;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Evento {
    private String nombre;
    private Date fecha;
    private Ubicacion ubicacion;
    private List<Atuendo> sugeridos = new ArrayList<Atuendo>();
    private Atuendo seleccionado;

    public Evento() {
    }

    public Evento (String nombre, Date fecha, Ubicacion ubicacion){
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

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public void setUbicacion(Ubicacion ubicacion) {
        this.ubicacion = ubicacion;
    }

    public void setUbicacion(Context context,String direccion) throws IOException {
        Geocoder geocoder = new Geocoder(context, new Locale.Builder().setRegion("AR").build());
        Address address =  geocoder.getFromLocationName(direccion, 1).get(0);
        Ubicacion ubicacion = new Ubicacion(address.getLatitude(), address.getLongitude());
        this.ubicacion = ubicacion;
    }

    public List<Atuendo> getSugeridos() {
        return sugeridos;
    }

    public void setSugeridos(List<Atuendo> sugeridos) {
        this.sugeridos = sugeridos;
    }

    public Atuendo getSeleccionado() {
        return seleccionado;
    }

    public void setSeleccionado(Atuendo seleccionado) {
        this.seleccionado = seleccionado;
    }
}
