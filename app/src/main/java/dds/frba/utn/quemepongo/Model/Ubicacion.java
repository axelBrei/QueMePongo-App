package dds.frba.utn.quemepongo.Model;

public class Ubicacion {
    private float latitud;
    private float longitud;
    private float radio;


    public Ubicacion(double latitud, double longitud) {
        this.latitud= (float) latitud;
        this.longitud = (float)longitud;
        this.radio = 5.0f;
    }

    public Ubicacion getUbicacion(){return this;}

    public float getLatitud() {
        return this.latitud;
    }

    public float getLongitud(){
        return this.longitud;
    }

    public  float getRadio(){
        return  this.radio;
    }

}
