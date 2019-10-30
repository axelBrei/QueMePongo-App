package dds.frba.utn.quemepongo.View.Activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.widget.AppCompatButton;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;

import dds.frba.utn.quemepongo.Controllers.GuardarropaController;
import dds.frba.utn.quemepongo.Helpers.ErrorHelper;
import dds.frba.utn.quemepongo.Helpers.RetrofitInstanciator;
import dds.frba.utn.quemepongo.Model.Guardarropa;
import dds.frba.utn.quemepongo.QueMePongo;
import dds.frba.utn.quemepongo.R;
import dds.frba.utn.quemepongo.Repository.GuardarropasRepository;
import dds.frba.utn.quemepongo.Utils.ActivityHelper;
import dds.frba.utn.quemepongo.Utils.CustomListenners.OnCompleteListenerWithStatusAndApplication;
import dds.frba.utn.quemepongo.Utils.CustomListenners.OnCompleteListenner;
import dds.frba.utn.quemepongo.View.QueMePongoActivity;
import dds.frba.utn.quemepongo.View.Toolbar.ToolbarView;

public class CrearGuardarropaActivity extends QueMePongoActivity {
    public static final String SHOW_INTRO_TEXT = "SHOW_INTRO_TEXT";

    private TextInputEditText nombre;
    private AppCompatButton botonContinuar;

    private GuardarropaController guardarropaController;
    private QueMePongo application;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        enableBackButton();
        setToolbarTitle("Crear Guardarropa");
        LinearLayout introTextContainer = findViewById(R.id.crearGuardarropaIntroText);
        TextInputLayout nombreLayout = findViewById(R.id.crearGuardarropaNombreLayout);
        nombre = findViewById(R.id.crearGuardarropaNombre);
        botonContinuar = findViewById(R.id.crearGuardarropaButton);

        guardarropaController = new GuardarropaController(_activity);
        application = (QueMePongo)getApplication();

        Bundle bundle = getIntent().getExtras();
        if(bundle == null){
            bundle = new Bundle();
        }
        Boolean primerGuardarropa =  bundle.getBoolean(SHOW_INTRO_TEXT, false);
        if(primerGuardarropa){
            introTextContainer.setVisibility(View.VISIBLE);
        }

        botonContinuar.setOnClickListener( v -> {
            String nombreGuardarropa = nombre.getText().toString();
            if (nombreGuardarropa.isEmpty()){
                nombreLayout.setError("Debe ingresar el nombre para continuar");
            }else {
                setProgressDialog(true);
                guardarropaController.agregarNuevoGuardarropa(
                        nombreGuardarropa,
                        new OnCompleteListenerWithStatusAndApplication() {
                            @Override
                            public void onComplete(Boolean success, QueMePongo application, Object obj) {
                                if(success){
                                    Guardarropa guardarropa = new Guardarropa( (Integer) obj, nombre.getText().toString());
                                    application.addGuardarropa(guardarropa);

                                    if(primerGuardarropa)
                                        ActivityHelper.startActivity(_activity, MainActivity.class);
                                    else
                                        onBackPressed();
                                }
                            }
                        }
                );
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
