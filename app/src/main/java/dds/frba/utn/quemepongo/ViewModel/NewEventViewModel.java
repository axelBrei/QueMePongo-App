package dds.frba.utn.quemepongo.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import dds.frba.utn.quemepongo.Model.Evento;
import dds.frba.utn.quemepongo.Model.WebServices.Response.Geocoding.NominatimGeocodeResponse;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class NewEventViewModel extends AndroidViewModel {
    String name;
    Calendar since;
    Calendar until;
    String frecuency;
    String formalidad;
    Double latitud;
    Double longitud;
    String nombreUbicacion;
    Integer idGuardarropa;

    public NewEventViewModel(@NonNull Application application) {
        super(application);
    }

    public void setLocation(NominatimGeocodeResponse location){

        latitud = Double.parseDouble(location.getLat());
        longitud = Double.parseDouble(location.getLon());
        nombreUbicacion = location.getDisplay_name();

    }

    public Evento buildEvent(){
        Evento evento = new Evento();
        evento.setNombre(name);
        evento.setDesde(since.getTime());
        evento.setHasta(until.getTime());
        evento.setFrecuencia(frecuency);
        evento.setFormalidad(formalidad);
        evento.setLatitud(latitud);
        evento.setLongitud(longitud);
        evento.setNombreUbicacion(nombreUbicacion);
        evento.setId_guardarropa(idGuardarropa);
        return evento;
    }
}
