package dds.frba.utn.quemepongo.Controllers;

import android.content.Context;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import dds.frba.utn.quemepongo.Helpers.CustomRetrofitCallback;
import dds.frba.utn.quemepongo.Helpers.ErrorHelper;
import dds.frba.utn.quemepongo.Helpers.RetrofitInstanciator;
import dds.frba.utn.quemepongo.Model.WebServices.Error;
import dds.frba.utn.quemepongo.Model.WebServices.Request.Guardarropa.CompartirGuardarropaRequest;
import dds.frba.utn.quemepongo.Model.WebServices.Request.Guardarropa.GetGuardarropaRequest;
import dds.frba.utn.quemepongo.QueMePongo;
import dds.frba.utn.quemepongo.Repository.GuardarropasRepository;
import dds.frba.utn.quemepongo.Utils.CustomListenners.OnCompleteListenerWithStatus;
import dds.frba.utn.quemepongo.Utils.CustomListenners.OnCompleteListenerWithStatusAndApplication;
import dds.frba.utn.quemepongo.Utils.CustomListenners.OnCompleteListenner;
import retrofit2.Call;
import retrofit2.Response;


public class GuardarropaController {
    GuardarropasRepository repository;
    QueMePongo application;

    public GuardarropaController(Context context) {
        repository = RetrofitInstanciator.instanciateRepository(GuardarropasRepository.class);
        application = (QueMePongo) context.getApplicationContext();

    }

    public void getGuardarropasDelCliente(OnCompleteListenerWithStatusAndApplication listener){
        repository
                .getGuardarropasDelCliente(new GetGuardarropaRequest(FirebaseAuth.getInstance().getCurrentUser().getUid()))
                .enqueue(
                        new ErrorHelper().showCallbackErrorIfNeed(
                                application,
                                response -> listener.onComplete(true, application, response),
                                error -> listener.onComplete(false, application, error)
                        )
                );
    }

    public void agregarNuevoGuardarropa(String descripcion, OnCompleteListenerWithStatusAndApplication listener) {
        repository
                .crearGuardarropa(FirebaseAuth.getInstance().getCurrentUser().getUid(), descripcion)
                .enqueue(
                        new ErrorHelper().showCallbackErrorIfNeed(
                                application,
                                guardarropaId -> listener.onComplete(true, application, guardarropaId),
                                error -> listener.onComplete(false, application, error)
                        )
                );
    }

    public void borrarGuardarropas(int idGuardarropa, OnCompleteListenerWithStatusAndApplication listener) {
        repository
                .borrarGuardarropa(FirebaseAuth.getInstance().getCurrentUser().getUid(), idGuardarropa)
                .enqueue(
                        new ErrorHelper().showCallbackErrorIfNeed(
                                application,
                                response -> listener.onComplete(true, application, response),
                                error -> listener.onComplete(false, application, error)
                        )
                );
    }

    public void getGuardarropaCompleto(int idGuardarropa, OnCompleteListenerWithStatusAndApplication listener) {
        repository
                .getGuardarropas(FirebaseAuth.getInstance().getCurrentUser().getUid(),idGuardarropa)
                .enqueue(
                        new ErrorHelper().showCallbackErrorIfNeed(
                                application,
                                response -> listener.onComplete(true, application, response),
                                error -> listener.onComplete(false, application, error)
                        )
                );
    }

    public void compartirGuardarropa(int idGuardarropa, String toUser, OnCompleteListenerWithStatus listener) {
        CompartirGuardarropaRequest request = new CompartirGuardarropaRequest(
                FirebaseAuth.getInstance().getCurrentUser().getUid(),
                toUser,
                idGuardarropa
        );
        repository
                .compartirGuardarropa(request)
                .enqueue(
                        new ErrorHelper().showCallbackErrorIfNeed(
                                application,
                                (obj) -> listener.onComplete(true, obj),
                                (err) -> listener.onComplete(false,err)
                        )
                );
    }

    public void dejarDeCompartirGuardarropa(int idGuardarropa, String toUser, OnCompleteListenerWithStatus listener) {
        CompartirGuardarropaRequest request = new CompartirGuardarropaRequest(
                FirebaseAuth.getInstance().getCurrentUser().getUid(),
                toUser,
                idGuardarropa);
        repository.dejarDeCompartirGuardarropa(request)
                .enqueue(new CustomRetrofitCallback<Void>() {
                    @Override
                    public void onCustomResponse(Call<Void> call, Response<Void> response) {
                        listener.onComplete(true, response.body());
                    }

                    @Override
                    public void onCustomFailure(Call<Void> call, Error error) {
                        listener.onComplete(false, error);
                    }

                    @Override
                    public void onHttpRequestFail(Call<Void> call, Throwable t) {
                        Toast.makeText(application, "Ha ocurrido un error al intentar dejar de compartir su guardarropa", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void getCompartidos(Integer idGuardarropa, OnCompleteListenerWithStatus listenerWithStatus){
        repository.getCompoartidosGuardarropa(FirebaseAuth.getInstance().getCurrentUser().getUid(), idGuardarropa)
                .enqueue(new ErrorHelper().showCallbackErrorIfNeed(
                        application,
                        list -> listenerWithStatus.onComplete(true, list),
                        er -> listenerWithStatus.onComplete(false, er)
                ));
    }
}
