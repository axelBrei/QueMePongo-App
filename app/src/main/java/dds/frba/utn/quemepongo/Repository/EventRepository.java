package dds.frba.utn.quemepongo.Repository;

import dds.frba.utn.quemepongo.Model.Evento;
import dds.frba.utn.quemepongo.Model.WebServices.Request.Evento.SendEventoRequest;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface EventRepository {

    @POST("evento/atuendo")
    Call<Void> sendCurrentEvent(@Body SendEventoRequest evento);
}
