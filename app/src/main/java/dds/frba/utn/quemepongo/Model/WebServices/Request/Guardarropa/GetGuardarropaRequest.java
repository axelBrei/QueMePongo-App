package dds.frba.utn.quemepongo.Model.WebServices.Request.Guardarropa;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults( level = AccessLevel.PRIVATE)
public class GetGuardarropaRequest {
    String uid;
}
