package dds.frba.utn.quemepongo.Repository;

import java.util.List;

import dds.frba.utn.quemepongo.Model.Evento;
import dds.frba.utn.quemepongo.Model.WebServices.Request.Evento.AbmEvento;
import dds.frba.utn.quemepongo.Model.WebServices.Request.Evento.SendEventoRequest;
import dds.frba.utn.quemepongo.Model.WebServices.Response.Evento.NewEventResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface EventosRepository {

    @GET("evento/getEventos")
    Call<List<Evento>> getEventosDelCliente(@Query("uid") String userId);

    @POST("evento/agregar")
    Call<NewEventResponse> crearEvento(@Body SendEventoRequest body);

    @POST("evento/eliminar")
    Call<Void> eliminarEvento(@Body AbmEvento body);

    @POST("evento/eliminarFrecuencia")
    Call<Void> eliminarFrecuenciaEvento(@Body AbmEvento body);
}
