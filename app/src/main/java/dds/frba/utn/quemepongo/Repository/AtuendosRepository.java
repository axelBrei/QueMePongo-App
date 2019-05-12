package dds.frba.utn.quemepongo.Repository;

import dds.frba.utn.quemepongo.Model.Atuendo;
import dds.frba.utn.quemepongo.Model.WebServices.Request.Atuendo.GetAtuendoRequest;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AtuendosRepository {

    @POST("atuendo/getRecomendadosDesdeGuardaropa")
    Call<Atuendo> getAtuendo(@Body GetAtuendoRequest request);
}
