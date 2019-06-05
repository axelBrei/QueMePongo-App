package dds.frba.utn.quemepongo.View.Activity;

import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.LinearLayout;

import com.google.firebase.auth.FirebaseAuth;

import dds.frba.utn.quemepongo.Helpers.CustomRetrofitCallback;
import dds.frba.utn.quemepongo.Helpers.ErrorHelper;
import dds.frba.utn.quemepongo.Helpers.RetrofitInstanciator;
import dds.frba.utn.quemepongo.Model.Guardarropa;
import dds.frba.utn.quemepongo.QueMePongo;
import dds.frba.utn.quemepongo.R;
import dds.frba.utn.quemepongo.Repository.GuardarropasRepository;
import dds.frba.utn.quemepongo.Utils.OnCompleteListenner;
import dds.frba.utn.quemepongo.View.QueMePongoActivity;
import retrofit2.Call;
import retrofit2.Response;

public class CrearGuardarropaActivity extends QueMePongoActivity {
    public static final String SHOW_INTRO_TEXT = "SHOW_INTRO_TEXT";

    private TextInputEditText nombre;
    private AppCompatButton botonContinuar;

    private GuardarropasRepository repository;
    private QueMePongo application;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LinearLayout introTextContainer = findViewById(R.id.crearGuardarropaIntroText);
        TextInputLayout nombreLayout = findViewById(R.id.crearGuardarropaNombreLayout);
        nombre = findViewById(R.id.crearGuardarropaNombre);
        botonContinuar = findViewById(R.id.crearGuardarropaButton);

        repository = RetrofitInstanciator.getInstance().getRetrofit().create(GuardarropasRepository.class);
        application = (QueMePongo)getApplication();

        botonContinuar.setOnClickListener( v -> {
            String nombreGuardarropa = nombre.getText().toString();
            if (nombreGuardarropa.isEmpty()){
                nombreLayout.setError("Debe ingresar el nombre para continuar");
            }else {
                repository.crearGuardarropa(FirebaseAuth.getInstance().getCurrentUser().getUid(), nombreGuardarropa)
                        .enqueue(getRestrofitCallback());
            }
        });
        nombre.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                nombreLayout.setError("");
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        Bundle bundle = getIntent().getExtras();
        if(bundle != null && bundle.getBoolean(SHOW_INTRO_TEXT, false)){
         introTextContainer.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected int getView() {
        return R.layout.activity_crear_guardarropa;
    }

    @Override
    protected boolean enableToolbarSpinner() {
        return false;
    }

    private CustomRetrofitCallback<Integer> getRestrofitCallback(){
        setProgressDialog(true);
        return ErrorHelper.showCallbackErrorIfNeed(_activity, new OnCompleteListenner<Response<Integer>>() {
            @Override
            public void onComplete(Response<Integer> param) {
                Guardarropa guardarropa = new Guardarropa(param.body(), nombre.getText().toString());
                application.addGuardarropa(guardarropa);
                Intent intent = new Intent(_activity, MainActivity.class);
                startActivity(intent);

            }
        });
    }
}