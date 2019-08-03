package dds.frba.utn.quemepongo.Model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import dds.frba.utn.quemepongo.Utils.JsonParser.PrendaDeserializer;
import dds.frba.utn.quemepongo.Utils.JsonParser.PrendaSerializer;
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
//@JsonSerialize(using = PrendaSerializer.class)
//@JsonDeserialize(using = PrendaDeserializer.class)
public class Prenda {

    Integer id = null;
    String tipoDeTela = "";
    String descripcion = "";
    String colorP = "";
    String colorS = "";
    Double abrigo = 0.0;
    String tipoDePrenda = "";
    // Ej. Superior-4, Inferior
    Integer indiceSuperposicion;
    Boolean reservada;
}
