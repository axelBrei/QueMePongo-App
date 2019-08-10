package dds.frba.utn.quemepongo.View.Activity;

import android.arch.lifecycle.ViewModelProviders;
import android.support.design.button.MaterialButton;
import android.support.design.widget.TextInputEditText;
import android.os.Bundle;
import android.support.v7.widget.AppCompatSpinner;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.Toast;

import com.chivorn.smartmaterialspinner.SmartMaterialSpinner;
import com.google.firebase.auth.FirebaseAuth;

import java.util.HashMap;
import java.util.List;

import dds.frba.utn.quemepongo.Adapters.SpinnerArrayAdapter;
import dds.frba.utn.quemepongo.Controllers.JsonController;
import dds.frba.utn.quemepongo.Helpers.ErrorHelper;
import dds.frba.utn.quemepongo.Model.WebServices.Response.Prendas.AddPrendaResponse;
import dds.frba.utn.quemepongo.QueMePongo;
import dds.frba.utn.quemepongo.R;
import dds.frba.utn.quemepongo.Utils.CustomOnItemSelectedListener;
import dds.frba.utn.quemepongo.Utils.OnCompleteListenner;
import dds.frba.utn.quemepongo.View.QueMePongoActivity;
import dds.frba.utn.quemepongo.View.Toolbar.ToolbarView;
import dds.frba.utn.quemepongo.ViewModel.CrearPrendasViewModel;
import retrofit2.Call;
import retrofit2.Response;

import static dds.frba.utn.quemepongo.ViewModel.CrearPrendasViewModel.FORMALIDAD;
import static dds.frba.utn.quemepongo.ViewModel.CrearPrendasViewModel.PRIMARY_COLOR_TYPE;
import static dds.frba.utn.quemepongo.ViewModel.CrearPrendasViewModel.SECONDARY_COLOR_TYPE;
import static dds.frba.utn.quemepongo.ViewModel.CrearPrendasViewModel.TIPO_DE_TELA;
import static dds.frba.utn.quemepongo.ViewModel.CrearPrendasViewModel.TIPO_PARTE_QUE_OCUPA;
import static dds.frba.utn.quemepongo.ViewModel.CrearPrendasViewModel.TIPO_PRENDA_SUPERIOR;

public class CrearPrendasActivity extends QueMePongoActivity implements CustomOnItemSelectedListener.OnItemSelectedCustom{
    // MODEL
    private CrearPrendasViewModel viewModel;
    // UI
    private SmartMaterialSpinner primaryColorSpinner;
    private SmartMaterialSpinner secondaryColorSpinner;
    private SmartMaterialSpinner telasSpinner;
    private SmartMaterialSpinner tipoDePrendaSpinner;
    private SmartMaterialSpinner tipoDePrendaSuperiorSpinner;
    private SmartMaterialSpinner formalidadSpinner;
    private TextInputEditText descripcionEditText;
    private MaterialButton uploadButton;
    
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // BUSCO EN EL XML LOS COMPONENTES
        telasSpinner = findViewById(R.id.CrearPrendaTelasSpinner);
        primaryColorSpinner = findViewById(R.id.CrearPrendaPrimaryoloresSpiner);
        secondaryColorSpinner = findViewById(R.id.CrearPrendaSecondaryColoresSpiner);
        tipoDePrendaSpinner = findViewById(R.id.CrearPrendaParteQueOcupaSpinner);
        tipoDePrendaSuperiorSpinner = findViewById(R.id.CrearPrendaTipoSuperior);
        formalidadSpinner = findViewById(R.id.CrearPrendaFormalidadSpinner);
        descripcionEditText = findViewById(R.id.CrearPrendaDescripcion);
        uploadButton = findViewById(R.id.CrearPrendaUploadButton);

        viewModel = ViewModelProviders.of(this).get(CrearPrendasViewModel.class);
        JsonController controller = new JsonController(this);
        viewModel.setColores(controller.getColors());
        viewModel.setTiposDeTela(controller.getTiposDeTela());

        initUI();
        ToolbarView.setToolbarTitle(_activity, "Crear prenda");
    }

    @Override
    protected int getView() {
        return R.layout.activity_crear_prendas;
    }

    @Override
    protected boolean enableToolbarSpinner() { return false; }

    private void initUI(){
        uploadButton.setOnClickListener( (v) -> {
            setProgressDialog(true);
            viewModel.setPrendaField(CrearPrendasViewModel.DESCRIPCION_PRENDA, descripcionEditText.getText().toString());
            HashMap<String, Object> request = new HashMap<>();
            request.put("prenda", viewModel.getPrenda());
            request.put("uid", FirebaseAuth.getInstance().getCurrentUser().getUid());
            request.put("idGuardarropa", ( (QueMePongo)viewModel.getApplication()).getGuardarropaActual().getValue().getId());
            Call<HashMap<Object, Object>> c = viewModel.getPrendasRepository().anadirPrenda(request);
            c.enqueue(new ErrorHelper().showCallbackErrorIfNeed(_activity,
                    new OnCompleteListenner<HashMap<Object, Object>>() {
                        @Override
                        public void onComplete(HashMap<Object, Object> param) {
                            setProgressDialog(false);
                            Integer prendaId = Float.floatToIntBits(Float.parseFloat(param.get("idPrenda").toString()));
                            viewModel.getApplication().addPrendaToGuardarropa(viewModel.getPrendaGenerada(prendaId));
                            onBackPressed();
                        }
                    }
            ));
        });

        initSpinners();
        enableBackButton();
    }
    

    private void initSpinners(){
        primaryColorSpinner.setItems(viewModel.getColores());
        secondaryColorSpinner.setItems(viewModel.getColores());
        tipoDePrendaSpinner.setItems(viewModel.getPartesQueOcupa());
        telasSpinner.setItems(viewModel.getTiposDeTela());
        tipoDePrendaSuperiorSpinner.setItems(viewModel.getTiposSuperiores());
        formalidadSpinner.setItems(viewModel.getFormalidades());
        initSpinnerData();
        setSpinnerClickListeners();
    }

    private void initSpinnerData(){
        formalidadSpinner.setSelection(1);
    }

    private void setSpinnerClickListeners(){
        primaryColorSpinner.setOnItemSelectedListener(new CustomOnItemSelectedListener(this,PRIMARY_COLOR_TYPE));
        secondaryColorSpinner.setOnItemSelectedListener(new CustomOnItemSelectedListener(this,SECONDARY_COLOR_TYPE));
        telasSpinner.setOnItemSelectedListener(new CustomOnItemSelectedListener(this, TIPO_DE_TELA));
        tipoDePrendaSpinner.setOnItemSelectedListener(new CustomOnItemSelectedListener(this, TIPO_PARTE_QUE_OCUPA));
        formalidadSpinner.setOnItemSelectedListener(new CustomOnItemSelectedListener(this, FORMALIDAD));
        tipoDePrendaSuperiorSpinner.setOnItemSelectedListener(new CustomOnItemSelectedListener(new CustomOnItemSelectedListener.OnItemSelectedCustom() {
            @Override
            public void onItemSelectedCustom(AdapterView<?> parent, View view, int position, long id, String spinnerType) {
                viewModel.setPrendaField(spinnerType, position + 1);
            }
        }, TIPO_PRENDA_SUPERIOR));
    }

    @Override
    public void onItemSelectedCustom(AdapterView<?> parent, View view, int position, long id, String spinnerType) {
        String val = parent.getItemAtPosition(position).toString();
        if(spinnerType.equals(TIPO_PARTE_QUE_OCUPA)){
            Boolean esSuperior = val.equals("Superior");
            tipoDePrendaSuperiorSpinner.setVisibility(esSuperior ? View.VISIBLE : View.INVISIBLE);
            if(!esSuperior) tipoDePrendaSuperiorSpinner.setSelection(0);
        }
        viewModel.setPrendaField(spinnerType, val);
    }
}
