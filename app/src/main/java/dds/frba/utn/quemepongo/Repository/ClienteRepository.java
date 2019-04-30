package dds.frba.utn.quemepongo.Repository;

import dds.frba.utn.quemepongo.Model.Cliente;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ClienteRepository {

    @POST("/cliente/nuevo")
    Call<Void> nuevoCliente(@Body Cliente cli);
}
