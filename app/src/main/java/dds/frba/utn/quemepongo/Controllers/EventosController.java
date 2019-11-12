package dds.frba.utn.quemepongo.Controllers;

import android.content.Context;

import com.google.firebase.auth.FirebaseAuth;

import dds.frba.utn.quemepongo.Helpers.ErrorHelper;
import dds.frba.utn.quemepongo.Helpers.RetrofitInstanciator;
import dds.frba.utn.quemepongo.Model.Evento;
import dds.frba.utn.quemepongo.Model.WebServices.Request.Evento.AbmEvento;
import dds.frba.utn.quemepongo.Model.WebServices.Request.Evento.SendEventoRequest;
import dds.frba.utn.quemepongo.Model.WebServices.Response.Evento.NewEventResponse;
import dds.frba.utn.quemepongo.QueMePongo;
import dds.frba.utn.quemepongo.Repository.EventosRepository;
import dds.frba.utn.quemepongo.Utils.CustomListenners.OnCompleteListenerWithStatus;
import retrofit2.Call;

public class EventosController {

    EventosRepository repository;
    QueMePongo application;

    public EventosController(Context context) {
        this.repository = RetrofitInstanciator.instanciateRepository(EventosRepository.class);
        application = (QueMePongo) context.getApplicationContext();
    }

    public void getEventos(OnCompleteListenerWithStatus listenerWithStatus){
        repository.getEventosDelCliente(
                FirebaseAuth.getInstance().getCurrentUser().getUid()
        ).enqueue(
                new ErrorHelper().showCallbackErrorIfNeed(
                        application,
                        resp -> listenerWithStatus.onComplete(true, resp),
                        err -> listenerWithStatus.onComplete(false,err)
                )
        );
    }

    public void crearEvento(Evento evento, OnCompleteListenerWithStatus listenerWithStatus){
        SendEventoRequest request = new SendEventoRequest();
                request.setEvento(evento);
                request.setUid(FirebaseAuth.getInstance().getCurrentUser().getUid());
        Call<NewEventResponse> call = repository.crearEvento(request);
        call.enqueue(new ErrorHelper().showCallbackErrorIfNeed(application,
                    resp -> listenerWithStatus.onComplete(true, resp),
                    err -> listenerWithStatus.onComplete(false,err)
                ));
    }

    public void eliminarEvento(long idEvento, OnCompleteListenerWithStatus listenerWithStatus){
        AbmEvento body = new AbmEvento();
        body.setUid(FirebaseAuth.getInstance().getCurrentUser().getUid());
        body.setIdEvento(idEvento);
        repository
                .eliminarEvento(body)
                .enqueue(
                        new ErrorHelper().showToastErrorInCaseIsNeeded(
                                application,
                                listenerWithStatus
                        )
                );
    }

    public void eliminarFrecuenciaEvento(Evento evento, OnCompleteListenerWithStatus listenerWithStatus){
        AbmEvento body = new AbmEvento();
        body.setUid(FirebaseAuth.getInstance().getCurrentUser().getUid());
        body.setIdEvento(Long.parseLong(evento.getUidEvento()));
        body.setDesde(evento.getDesde());
        repository
                .eliminarFrecuenciaEvento(body)
                .enqueue(
                        new ErrorHelper().showToastErrorInCaseIsNeeded(
                                application,
                                listenerWithStatus
                        )
                );
    }
}
