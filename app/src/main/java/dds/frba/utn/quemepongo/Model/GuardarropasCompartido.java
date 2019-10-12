package dds.frba.utn.quemepongo.Model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Data
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
public class GuardarropasCompartido {
    String uidCompartido;
    Integer idGuardarropa;
    String nombreGuardarropa;
    String nombreCompartido;
}
