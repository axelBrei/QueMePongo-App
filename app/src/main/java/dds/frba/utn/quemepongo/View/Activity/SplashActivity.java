package dds.frba.utn.quemepongo.View.Activity;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;

import com.google.firebase.auth.FirebaseAuth;

import dds.frba.utn.quemepongo.Helpers.ErrorHelper;
import dds.frba.utn.quemepongo.Helpers.RetrofitInstanciator;
import dds.frba.utn.quemepongo.Model.Schedulable;
import dds.frba.utn.quemepongo.Model.WebServices.Error;
import dds.frba.utn.quemepongo.Model.WebServices.Request.Guardarropa.GetGuardarropaRequest;
import dds.frba.utn.quemepongo.Model.WebServices.Response.Guardarropa.GetGuardarropasResponse;
import dds.frba.utn.quemepongo.QueMePongo;
import dds.frba.utn.quemepongo.Repository.GuardarropasRepository;
import dds.frba.utn.quemepongo.Utils.ActivityHelper;
import dds.frba.utn.quemepongo.Utils.OnCompleteListenner;

public class SplashActivity extends AppCompatActivity {
    private static GuardarropasRepository repository;
    private static FirebaseAuth mAuth;
    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        repository = RetrofitInstanciator.getInstance().getRetrofit().create(GuardarropasRepository.class);

        if(mAuth.getCurrentUser() != null){
            QueMePongo application = ((QueMePongo) getApplication());
            SplashActivity.fectchGuardarropas(application, param -> {

                Intent inten = new Intent(SplashActivity.this, MainActivity.class);

                if (param.getGuardarropas().isEmpty()) {
                    Bundle bundle = new Bundle();
                    bundle.putBoolean(CrearGuardarropaActivity.SHOW_INTRO_TEXT, true);
                    inten = ActivityHelper.startActivityWithBacbButtonBlocked(
                            SplashActivity.this,
                            CrearGuardarropaActivity.class,
                            bundle);
                }else {
                    application.setGuardarropas(param);
                }
                startActivity(inten);

            }, new OnCompleteListenner<Error>() {
                @Override
                public void onComplete(Error param) {
                    //TODO send retry to the execute again the failed method
                }
            });
        }else{
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }
    }

    public static void fectchGuardarropas(
            Schedulable schedulable,
            OnCompleteListenner<GetGuardarropasResponse> succedListener,
            @Nullable OnCompleteListenner<Error> errorListenner){
        if(mAuth.getCurrentUser() != null){
            repository
            .getGuardarropasDelCliente(
                            new GetGuardarropaRequest(
                                    mAuth.getCurrentUser().getUid())
                    )
            .enqueue(new ErrorHelper().showCallbackErrorIfNeed(schedulable,
                    new OnCompleteListenner<GetGuardarropasResponse>() {
                        @Override
                        public void onComplete(GetGuardarropasResponse param) {
                            succedListener.onComplete(param);
                        }
                    },
                    new OnCompleteListenner<Error>() {
                        @Override
                        public void onComplete(Error param) {
                            if(errorListenner != null)
                                errorListenner.onComplete(param);
                        }
                    }));
        }
    }


}
