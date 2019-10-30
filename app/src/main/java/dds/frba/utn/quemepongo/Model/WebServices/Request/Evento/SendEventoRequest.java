package dds.frba.utn.quemepongo.Model.WebServices.Request.Evento;

import dds.frba.utn.quemepongo.Model.Evento;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults( level = AccessLevel.PRIVATE)
@NoArgsConstructor
public class SendEventoRequest {
    String uid;
    Evento evento;
}
