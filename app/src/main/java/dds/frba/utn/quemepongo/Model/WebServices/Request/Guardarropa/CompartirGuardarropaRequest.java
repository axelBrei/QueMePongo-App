package dds.frba.utn.quemepongo.Model.WebServices.Request.Guardarropa;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level =  AccessLevel.PRIVATE)
@AllArgsConstructor
public class CompartirGuardarropaRequest {
    String uid;
    String uidDestino;
    int idGuardarropa;
}
