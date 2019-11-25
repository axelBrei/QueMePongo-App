package dds.frba.utn.quemepongo.Controllers;

import android.content.Context;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.List;

import dds.frba.utn.quemepongo.Helpers.ErrorHelper;
import dds.frba.utn.quemepongo.Helpers.RetrofitInstanciator;
import dds.frba.utn.quemepongo.Model.Atuendo;
import dds.frba.utn.quemepongo.QueMePongo;
import dds.frba.utn.quemepongo.Repository.AtuendosRespository;
import dds.frba.utn.quemepongo.Utils.CustomListenners.OnCompleteListenerWithStatus;
import retrofit2.Call;

public class AtuendosController {

    AtuendosRespository respository;
    QueMePongo application;

    public AtuendosController(Context context) {
        application = (QueMePongo) context.getApplicationContext();
        respository = RetrofitInstanciator.instanciateRepository(AtuendosRespository.class);
    }

    public void getAtuendosDelEvento(Long eventId, OnCompleteListenerWithStatus<List<Atuendo>> listenerWithStatus){
        Call<List<Atuendo>> call = respository.getAtuendosNotificados(eventId);
        call.enqueue(new ErrorHelper().showToastErrorInCaseIsNeeded(
                application,
                listenerWithStatus
        ));
    }

    public void reservarAtuendo(Atuendo atuendo, Long eventId, OnCompleteListenerWithStatus listenerWithStatus){
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        Call call = respository.reservarAtuendo(userId, eventId, atuendo.getId());
        call.enqueue(new ErrorHelper().showErrorInCaseIsNeeded(
                application,
                listenerWithStatus
        ));
    }
}
