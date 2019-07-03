package dds.frba.utn.quemepongo.Repository;

import dds.frba.utn.quemepongo.Model.WebServices.Request.Cliente.ClienteUidRequestBody;
import dds.frba.utn.quemepongo.Model.WebServices.Request.Guardarropa.GetGuardarropaRequest;
import dds.frba.utn.quemepongo.Model.WebServices.Response.Guardarropa.GetGuardarropasResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface PremiumRepository {

    @POST("cliente/premium")
//    @POST("http://www.mocky.io/v2/5cc7320d3200008734b9513e")
    Call pruebaPremium(@Body ClienteUidRequestBody body);

    @POST("cliente/gratuito")
    Call pruebaGratis(@Body ClienteUidRequestBody body);

}
