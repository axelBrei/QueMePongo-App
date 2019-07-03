package dds.frba.utn.quemepongo.View.Activity;

import android.content.Intent;
import android.content.IntentSender;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.AppCompatButton;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.LinearLayout;

import com.google.firebase.auth.FirebaseAuth;

import dds.frba.utn.quemepongo.Helpers.ErrorHelper;
import dds.frba.utn.quemepongo.Helpers.RetrofitInstanciator;
import dds.frba.utn.quemepongo.Model.Atuendo;
import dds.frba.utn.quemepongo.Model.Guardarropa;
import dds.frba.utn.quemepongo.Model.WebServices.Error;
import dds.frba.utn.quemepongo.Model.WebServices.Request.Cliente.ClienteUidRequestBody;
import dds.frba.utn.quemepongo.Model.WebServices.Response.Guardarropa.GetGuardarropasResponse;
import dds.frba.utn.quemepongo.QueMePongo;
import dds.frba.utn.quemepongo.R;
import dds.frba.utn.quemepongo.Repository.GuardarropasRepository;
import dds.frba.utn.quemepongo.Repository.PremiumRepository;
import dds.frba.utn.quemepongo.Utils.OnCompleteListenner;
import dds.frba.utn.quemepongo.View.QueMePongoActivity;

public class PruebaPremium extends QueMePongoActivity {
    public static final String SHOW_INTRO_TEXT = "SHOW_INTRO_TEXT";

    private TextInputEditText nombre;
    private AppCompatButton botonPremium;

    private PremiumRepository repository;
    private QueMePongo application;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);




        botonPremium = findViewById(R.id.pruebaPremiumButton);

        repository = RetrofitInstanciator.getInstance().getRetrofit().create(PremiumRepository.class);
        application = (QueMePongo)getApplication();

        botonPremium.setOnClickListener( v -> {

                setProgressDialog(true);
                repository.pruebaPremium(new ClienteUidRequestBody(FirebaseAuth.getInstance().getCurrentUser().getUid()) )
                        .enqueue(new ErrorHelper().showCallbackErrorIfNeed(_activity,
                                new OnCompleteListenner<Integer>() {
                                    @Override
                                    public void onComplete(Integer param) {
                                        setProgressDialog(false);
                                    }
                                }
                        ));
                }
    );




    }

    @Override
    protected int getView() {
        return R.layout.activity_crear_guardarropa;
    }

    @Override
    protected boolean enableToolbarSpinner() {
        return false;
    }

}
