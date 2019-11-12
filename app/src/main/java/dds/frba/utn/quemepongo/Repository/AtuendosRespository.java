package dds.frba.utn.quemepongo.Repository;

import java.util.List;

import dds.frba.utn.quemepongo.Model.Atuendo;
import dds.frba.utn.quemepongo.Model.WebServices.Request.Atuendo.GetAtuendosRequest;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface AtuendosRespository {

    @GET("atuendo/notificados")
    Call<List<Atuendo>> getAtuendosNotificados(@Query("idEvento") Long idEvento);

    @GET("reserva/reservarAtuendo")
    Call<Void> reservarAtuendo(
            @Query("uid") String uid,
            @Query("idEvento") Long idEvento,
            @Query("idAtuendo") Long idAtuendo
    );
}
