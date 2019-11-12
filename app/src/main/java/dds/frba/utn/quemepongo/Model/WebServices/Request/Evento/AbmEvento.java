package dds.frba.utn.quemepongo.Model.WebServices.Request.Evento;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AbmEvento {
    String uid;
    long idEvento;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    Date desde;
}
