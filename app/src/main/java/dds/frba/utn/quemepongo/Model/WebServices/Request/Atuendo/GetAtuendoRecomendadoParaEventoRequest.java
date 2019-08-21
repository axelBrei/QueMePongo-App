package dds.frba.utn.quemepongo.Model.WebServices.Request.Atuendo;


import dds.frba.utn.quemepongo.Model.Evento;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@FieldDefaults( level = AccessLevel.PRIVATE)
public class GetAtuendoRecomendadoParaEventoRequest {
    String username;
    Integer idGuardarropa;
    Evento evento;
    Integer climaApi=0;

}
