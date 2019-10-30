package dds.frba.utn.quemepongo.View.Activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.google.firebase.auth.FirebaseAuth;

import dds.frba.utn.quemepongo.Controllers.GuardarropaController;
import dds.frba.utn.quemepongo.Helpers.ErrorHelper;
import dds.frba.utn.quemepongo.Helpers.RetrofitInstanciator;
import dds.frba.utn.quemepongo.Model.Schedulable;
import dds.frba.utn.quemepongo.Model.WebServices.Error;
import dds.frba.utn.quemepongo.Model.WebServices.Request.Guardarropa.GetGuardarropaRequest;
import dds.frba.utn.quemepongo.Model.WebServices.Response.Guardarropa.GetGuardarropasResponse;
import dds.frba.utn.quemepongo.QueMePongo;
import dds.frba.utn.quemepongo.Repository.GuardarropasRepository;
import dds.frba.utn.quemepongo.Utils.ActivityHelper;
import dds.frba.utn.quemepongo.Utils.CustomListenners.OnCompleteListenerWithStatus;
import dds.frba.utn.quemepongo.Utils.CustomListenners.OnCompleteListenerWithStatusAndApplication;
import dds.frba.utn.quemepongo.Utils.CustomListenners.OnCompleteListenner;

public class SplashActivity extends AppCompatActivity {
    private static GuardarropasRepository repository;
    private GuardarropaController guardarropaController;
    private static FirebaseAuth mAuth;
    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        guardarropaController = new GuardarropaController(SplashActivity.this);
        repository = RetrofitInstanciator.instanciateRepository(GuardarropasRepository.class);

        if(mAuth.getCurrentUser() != null){
            guardarropaController.getGuardarropasDelCliente(
                    new OnCompleteListenerWithStatusAndApplication() {
                        @Override
                        public void onComplete(Boolean succed, QueMePongo application, Object obj) {
                            if(succed){
                                GetGuardarropasResponse reponse = (GetGuardarropasResponse) obj;

                                if (reponse.getGuardarropas().isEmpty()) {
                                    Bundle bundle = new Bundle();
                                    bundle.putBoolean(CrearGuardarropaActivity.SHOW_INTRO_TEXT, true);
                                    ActivityHelper.startActivityWithBacbButtonBlocked(
                                            SplashActivity.this,
                                            CrearGuardarropaActivity.class,
                                            bundle);
                                }else {
                                    application.setGuardarropas(reponse);
                                    ActivityHelper.startActivity(SplashActivity.this, MainActivity.class);
                                }
                            }
                        }
                    }
            );
        }else{
            ActivityHelper.startActivity(SplashActivity.this, LoginActivity.class);
        }
    }
}
