package dds.frba.utn.quemepongo.Controllers;

import android.content.Context;

import com.google.firebase.auth.FirebaseAuth;

import dds.frba.utn.quemepongo.Helpers.CustomRetrofitCallback;
import dds.frba.utn.quemepongo.Helpers.ErrorHelper;
import dds.frba.utn.quemepongo.Helpers.RetrofitInstanciator;
import dds.frba.utn.quemepongo.Model.Evento;
import dds.frba.utn.quemepongo.Model.WebServices.Request.Evento.SendEventoRequest;
import dds.frba.utn.quemepongo.QueMePongo;
import dds.frba.utn.quemepongo.Repository.EventRepository;
import retrofit2.Call;

public class EventosController {
    private EventRepository repository;
    private Context context;
    private QueMePongo application;

    public EventosController(Context context) {
        repository = RetrofitInstanciator.getInstance().getRetrofit().create(EventRepository.class);
        this.context = context;
        application = (QueMePongo) context.getApplicationContext();
    }

    public void sendEvento(Evento evento, CustomRetrofitCallback<Void> callback){
        SendEventoRequest request = new SendEventoRequest();
        request.setUsername(FirebaseAuth.getInstance().getCurrentUser().getUid());
        request.setEvento(evento);
        request.setIdGuardarropa(String.valueOf(application.getGuardarropaActual().getValue().getId()));
        Call<Void> call = repository.sendCurrentEvent(request);
        call.enqueue(callback);
    }
}
