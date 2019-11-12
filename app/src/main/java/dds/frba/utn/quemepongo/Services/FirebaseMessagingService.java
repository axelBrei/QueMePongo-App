package dds.frba.utn.quemepongo.Services;

import android.content.Intent;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.RemoteMessage;

import dds.frba.utn.quemepongo.Helpers.RetrofitInstanciator;
import dds.frba.utn.quemepongo.Repository.ClienteRepository;
import dds.frba.utn.quemepongo.Utils.ActivityHelper;
import dds.frba.utn.quemepongo.Utils.CustomListenners.OnCompleteListenner;
import dds.frba.utn.quemepongo.View.Activity.SeleccionarAtuendoActivity;

public class FirebaseMessagingService extends com.google.firebase.messaging.FirebaseMessagingService {
    public FirebaseMessagingService() {
    }

    @Override
    public void onNewToken(String s) {
        super.onNewToken(s);
        RetrofitInstanciator
                .getInstance()
                .getRetrofit()
                .create(ClienteRepository.class)
                .actualizarToken(
                        FirebaseAuth.getInstance().getUid(),
                        s
                );
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        if(remoteMessage.getData().size() > 0){
            long idEvento = Long.parseLong(remoteMessage.getData().get("idEvento"));
            Intent intent = new Intent(this, SeleccionarAtuendoActivity.class);
            intent.putExtra("idEvento", idEvento);
            startActivity(intent);
        }
    }

    public static void getFirebaseToken(OnCompleteListenner<String> onCompleteListenner){
        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(
                instanceIdResult -> {
                    onCompleteListenner.onComplete(instanceIdResult.getToken());
                }
        );
    }
}
