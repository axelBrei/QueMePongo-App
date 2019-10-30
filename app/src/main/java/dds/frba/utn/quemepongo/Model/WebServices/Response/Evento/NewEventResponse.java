package dds.frba.utn.quemepongo.Model.WebServices.Response.Evento;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class NewEventResponse {
    String idEvento;
    String message;
}
