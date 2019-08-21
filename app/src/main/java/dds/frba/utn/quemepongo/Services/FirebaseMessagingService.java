package dds.frba.utn.quemepongo.Services;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.RemoteMessage;

import dds.frba.utn.quemepongo.Helpers.RetrofitInstanciator;
import dds.frba.utn.quemepongo.Repository.ClienteRepository;
import dds.frba.utn.quemepongo.Utils.CustomListenners.OnCompleteListenner;

public class FirebaseMessagingService extends com.google.firebase.messaging.FirebaseMessagingService {
    public FirebaseMessagingService() {
    }

    @Override
    public void onNewToken(String s) {
        super.onNewToken(s);
        // TODO mandar nuevo token al back, relacionado al firebase auth
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
        super.onMessageReceived(remoteMessage);
    }

    public static void getFirebaseToken(OnCompleteListenner<String> onCompleteListenner){
        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(
                instanceIdResult -> {
                    onCompleteListenner.onComplete(instanceIdResult.getToken());
                }
        );
    }
}
