package dds.frba.utn.quemepongo.Model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
public class Evento  implements Serializable {
    long id;
    // Por si google genera un id para el evento
    String uidEvento;
    String nombre;
    Date desde;
    Date hasta;
    String nombreUbicacion;
    Double latitud;
    Double longitud;
    String frecuencia;
    /*FORMAL O INFORMAL*/
    String formalidad;
    Boolean notificado = false;
    Atuendo atuendo;

    @Override
    public boolean equals(Object obj) {
        Evento evento = (Evento) obj;
        return this.id == evento.getId();
    }

    public boolean tieneReserva(){return atuendo!=null;}

    public Calendar toCalendar(){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(desde);
        return calendar;
    }
}
