package dds.frba.utn.quemepongo.Controllers;

import android.content.Context;

import com.google.firebase.auth.FirebaseAuth;

import java.util.HashMap;

import dds.frba.utn.quemepongo.Helpers.ErrorHelper;
import dds.frba.utn.quemepongo.Helpers.RetrofitInstanciator;
import dds.frba.utn.quemepongo.Model.Prenda;
import dds.frba.utn.quemepongo.Model.WebServices.Request.Prendas.DeletePrendaRequest;
import dds.frba.utn.quemepongo.QueMePongo;
import dds.frba.utn.quemepongo.Repository.PrendasRepository;
import dds.frba.utn.quemepongo.Utils.CustomListenners.OnCompleteListenerWithStatusAndApplication;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class PrendasController {
    QueMePongo application;
    PrendasRepository repository;
    String uid;
    int idGuardarropaActual;

    public PrendasController(Context context) {
        repository = RetrofitInstanciator.instanciateRepository(PrendasRepository.class);
        application = (QueMePongo) context.getApplicationContext();
        this.uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        idGuardarropaActual = application.getGuardarropaActual().getValue().getId();
    }

    public void agregarPrenda(HashMap<String,Object> prenda, OnCompleteListenerWithStatusAndApplication listener) {
        HashMap<String, Object> request = new HashMap<>();
        request.put("prenda", prenda);
        request.put("uid", uid);
        request.put("idGuardarropa", idGuardarropaActual);
        repository
                .anadirPrenda(request)
                .enqueue(
                        new ErrorHelper().showCallbackErrorIfNeed(
                                application,
                                response -> listener.onComplete(true, application, response),
                                error -> listener.onComplete(false, application, error)
                        )
                );
    }

    public void eliminarPrenda(int prendaId, OnCompleteListenerWithStatusAndApplication listener) {
        DeletePrendaRequest request = new DeletePrendaRequest(uid, idGuardarropaActual, prendaId);
        repository
                .eliminarPrenda(request)
                .enqueue(
                        new ErrorHelper().showCallbackErrorIfNeed(
                                application,
                                param -> listener.onComplete(true, application, null),
                                error -> listener.onComplete(false, application, error)
                        )
                );
    }
}
