package dds.frba.utn.quemepongo.Model.Utils;

import android.content.Context;

import com.applandeo.materialcalendarview.EventDay;

import java.util.Calendar;

import dds.frba.utn.quemepongo.Model.Evento;
import dds.frba.utn.quemepongo.R;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CustomEventDay {
    Evento evento;
    EventDay eventDay;

    public CustomEventDay(Context context,Evento evento) {
        this.evento = evento;
        eventDay = new EventDay(
                evento.toCalendar(),
                context.getResources().getDrawable(R.drawable.ic_lens_black_24dp),
                context.getResources().getColor(R.color.colorAccentLight)
        );
    }

}
