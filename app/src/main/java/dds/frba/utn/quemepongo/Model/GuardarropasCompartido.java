package dds.frba.utn.quemepongo.Model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Data
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
public class GuardarropasCompartido {
    String uidCompartido;
    Integer idGuardarropa;
    String nombreGuardarropa;
    String nombreCompartido;
}
