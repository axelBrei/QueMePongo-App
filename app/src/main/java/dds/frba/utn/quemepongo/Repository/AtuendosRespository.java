package dds.frba.utn.quemepongo.Repository;

import dds.frba.utn.quemepongo.Model.Atuendo;
import dds.frba.utn.quemepongo.Model.WebServices.Request.Atuendo.GetAtuendosRequest;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AtuendosRespository {

    @POST("/atuendo/getRecomendadosDesdeGuardaropa")
    Call<Atuendo> getAtuendoRecomendado(@Body GetAtuendosRequest body);
}
