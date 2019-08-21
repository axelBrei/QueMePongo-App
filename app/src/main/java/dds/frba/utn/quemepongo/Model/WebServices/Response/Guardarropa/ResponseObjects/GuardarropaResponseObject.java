package dds.frba.utn.quemepongo.Model.WebServices.Response.Guardarropa.ResponseObjects;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class GuardarropaResponseObject {
    int id;
    String descripcion;
}
