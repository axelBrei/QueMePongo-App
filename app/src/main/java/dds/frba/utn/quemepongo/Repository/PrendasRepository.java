package dds.frba.utn.quemepongo.Repository;

import java.util.HashMap;

import dds.frba.utn.quemepongo.Model.WebServices.Request.Prendas.DeletePrendaRequest;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface PrendasRepository {

    @POST("/prendas/addPrenda")
    Call<HashMap<Object, Object>> anadirPrenda(@Body HashMap<String, Object> body);

    @POST("prendas/deletePrenda")
    Call<Void> eliminarPrenda(@Body DeletePrendaRequest body);
}
