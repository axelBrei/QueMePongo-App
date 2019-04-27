package dds.frba.utn.quemepongo.Repository;

import java.util.List;

import dds.frba.utn.quemepongo.Model.Prenda;
import dds.frba.utn.quemepongo.Utils.JsonParser.PrendasContainer;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface PrendasRepository {

//    @POST("/prendas/getPrendas")
    @GET("http://www.mocky.io/v2/5cc0a58e310000f61c036462")
    Call<PrendasContainer> getPrendas();
}
