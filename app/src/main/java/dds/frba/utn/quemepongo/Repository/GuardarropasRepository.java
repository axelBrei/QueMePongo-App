package dds.frba.utn.quemepongo.Repository;

import java.util.List;

import dds.frba.utn.quemepongo.Model.Guardarropa;
import dds.frba.utn.quemepongo.Model.WebServices.Request.Guardarropa.GetGuardarropaRequest;
import dds.frba.utn.quemepongo.Model.WebServices.Response.Guardarropa.GetGuardarropasResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface GuardarropasRepository {

    @POST("guardaropa/getCantidad")
//    @POST("http://www.mocky.io/v2/5cc7320d3200008734b9513e")
    Call<GetGuardarropasResponse> getGuardarropasDelCliente(@Body GetGuardarropaRequest body);
}
