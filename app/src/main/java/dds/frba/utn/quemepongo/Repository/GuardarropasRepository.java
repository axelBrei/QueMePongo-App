package dds.frba.utn.quemepongo.Repository;

import java.util.List;

import dds.frba.utn.quemepongo.Model.Guardarropa;
import dds.frba.utn.quemepongo.Model.GuardarropasCompartido;
import dds.frba.utn.quemepongo.Model.WebServices.Request.Guardarropa.CompartirGuardarropaRequest;
import dds.frba.utn.quemepongo.Model.WebServices.Request.Guardarropa.GetGuardarropaRequest;
import dds.frba.utn.quemepongo.Model.WebServices.Response.Guardarropa.GetGuardarropasResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;


public interface GuardarropasRepository {

    @POST("guardaropa/getCantidad")
    Call<GetGuardarropasResponse> getGuardarropasDelCliente(@Body GetGuardarropaRequest body);

    @POST("guardaropa/nuevo")
    Call<Integer> crearGuardarropa(@Query("userName")String userId, @Query("descripcion")String nombreGuardarropa);

    @POST("guardaropa/delete")
    Call<Void> borrarGuardarropa(@Query("userName") String userId, @Query("id")int id);

    @GET("guardaropa/get")
    Call<Guardarropa> getGuardarropas(@Query("uid")String uid, @Query("idGuardarropa")   int idGuardarropa);

    @POST("guardaropa/compartir")
    Call<Void> compartirGuardarropa(@Body CompartirGuardarropaRequest request);

    @POST("guardaropa/dejarDeCompartir")
    Call<Void> dejarDeCompartirGuardarropa(@Body CompartirGuardarropaRequest request);

    @GET("guardaropa/getCompartidos")
    Call<List<GuardarropasCompartido>> getCompoartidosGuardarropa(@Query(("uid")) String uid, @Query(value = "idGuardarropa")Integer idGuardarropa);
}
