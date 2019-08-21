package dds.frba.utn.quemepongo.Controllers;

import android.app.Application;
import android.content.Context;

import com.google.firebase.auth.FirebaseAuth;

import dds.frba.utn.quemepongo.Helpers.CustomRetrofitCallback;
import dds.frba.utn.quemepongo.Helpers.ErrorHelper;
import dds.frba.utn.quemepongo.Helpers.RetrofitInstanciator;
import dds.frba.utn.quemepongo.Model.Cliente;
import dds.frba.utn.quemepongo.Model.WebServices.Error;
import dds.frba.utn.quemepongo.QueMePongo;
import dds.frba.utn.quemepongo.Repository.ClienteRepository;
import dds.frba.utn.quemepongo.Services.FirebaseMessagingService;
import dds.frba.utn.quemepongo.Utils.CustomListenners.OnCompleteListenerWithStatus;
import dds.frba.utn.quemepongo.Utils.CustomListenners.OnCompleteListenner;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@FieldDefaults( level = AccessLevel.PRIVATE)
public class ClienteController {
    ClienteRepository repository;
    Context context;
    QueMePongo application;

    public ClienteController(Context context) {
        application = (QueMePongo) context.getApplicationContext();
        this.context = context;
        repository = RetrofitInstanciator.instanciateRepository(ClienteRepository.class);
    }

    public void registrarClienteNuevo(Cliente cliente, OnCompleteListenerWithStatus listenerWithStatus){
        repository
                .nuevoCliente(cliente)
                .enqueue(new ErrorHelper().showCallbackErrorIfNeed(application,
                        param -> listenerWithStatus.onComplete(true, param),
                        param -> listenerWithStatus.onComplete(false, param)
                ));
    }

    public void actualizarFCMToken(String uid){
        FirebaseMessagingService.getFirebaseToken(
                token -> repository
                            .actualizarToken(uid, token)
                        .enqueue(new Callback<Void>() {
                            @Override
                            public void onResponse(Call<Void> call, Response<Void> response) {

                            }

                            @Override
                            public void onFailure(Call<Void> call, Throwable t) {

                            }
                        })
        );
    }
}
