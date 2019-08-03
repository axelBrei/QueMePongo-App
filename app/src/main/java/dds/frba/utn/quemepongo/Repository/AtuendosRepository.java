package dds.frba.utn.quemepongo.Repository;

import java.util.List;

import dds.frba.utn.quemepongo.Model.Atuendo;
import dds.frba.utn.quemepongo.Model.WebServices.Request.Atuendo.GetAtuendoRequest;
import dds.frba.utn.quemepongo.Utils.ListContainer;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AtuendosRepository {

    @POST("atuendo/getAtuendo")
    Call<List<Atuendo>> getAtuendo(@Body GetAtuendoRequest request);
}
