package dds.frba.utn.quemepongo.Model;

import java.io.Serializable;
import java.util.List;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Atuendo  implements Serializable {
    long id;
    float calificacion;
    List<Prenda> prendas;
    Double abrigo;

    public Atuendo(List<Prenda> prendas) {
        this.prendas = prendas;
    }
}
