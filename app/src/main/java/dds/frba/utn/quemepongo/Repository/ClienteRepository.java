package dds.frba.utn.quemepongo.Repository;

import dds.frba.utn.quemepongo.Model.Cliente;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;


public interface ClienteRepository {

    @POST("/cliente/nuevo")
    Call<Void> nuevoCliente(@Body Cliente cli);

    @GET("cliente/updateToken")
    Call<Void> actualizarToken(@Query("uid") String uid, @Query("userToken") String userToken);
}
