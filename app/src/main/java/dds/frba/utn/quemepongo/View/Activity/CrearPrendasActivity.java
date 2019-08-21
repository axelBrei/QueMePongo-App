package dds.frba.utn.quemepongo.View.Activity;

import android.arch.lifecycle.ViewModelProviders;
import android.support.design.button.MaterialButton;
import android.support.design.widget.TextInputEditText;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import com.chivorn.smartmaterialspinner.SmartMaterialSpinner;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Arrays;
import java.util.HashMap;

import dds.frba.utn.quemepongo.Controllers.JsonController;
import dds.frba.utn.quemepongo.Controllers.PrendasController;
import dds.frba.utn.quemepongo.Helpers.ErrorHelper;
import dds.frba.utn.quemepongo.QueMePongo;
import dds.frba.utn.quemepongo.R;
import dds.frba.utn.quemepongo.Utils.CustomListenners.OnCompleteListenerWithStatusAndApplication;
import dds.frba.utn.quemepongo.Utils.CustomOnItemSelectedListener;
import dds.frba.utn.quemepongo.Utils.CustomListenners.OnCompleteListenner;
import dds.frba.utn.quemepongo.View.QueMePongoActivity;
import dds.frba.utn.quemepongo.View.Toolbar.ToolbarView;
import dds.frba.utn.quemepongo.ViewModel.CrearPrendasViewModel;
import retrofit2.Call;

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
    private SmartMaterialSpinner abrigoSpinner;
    private TextInputEditText descripcionEditText;
    private MaterialButton uploadButton;

    private PrendasController prendasController;
    
    
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
        abrigoSpinner = findViewById(R.id.CrearPrendaAbrigo);
        descripcionEditText = findViewById(R.id.CrearPrendaDescripcion);
        uploadButton = findViewById(R.id.CrearPrendaUploadButton);

        viewModel = ViewModelProviders.of(this).get(CrearPrendasViewModel.class);
        JsonController controller = new JsonController(this);
        viewModel.setColores(controller.getColors());
        viewModel.setTiposDeTela(controller.getTiposDeTela());

        prendasController = new PrendasController(_activity);

        initUI();
        setToolbarTitle("Crear prenda");
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

            prendasController.agregarPrenda(viewModel.getPrenda(), new OnCompleteListenerWithStatusAndApplication() {
                @Override
                public void onComplete(Boolean success, QueMePongo application, Object obj) {
                    if(success){
                        HashMap<String, Object> response = (HashMap<String, Object>) obj;
                        setProgressDialog(false);
                        Integer prendaId = Float.floatToIntBits(Float.parseFloat(response.get("idPrenda").toString()));
                        viewModel.getApplication().addPrendaToGuardarropa(viewModel.getPrendaGenerada(prendaId));
                        onBackPressed();
                    }

                }
            });
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
        abrigoSpinner.setItems(Arrays.asList("1","2","3","4","5","6","7","8","9","10"));
        abrigoSpinner.showFloatingLabel();
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
        abrigoSpinner.setOnItemSelectedListener(new CustomOnItemSelectedListener(new CustomOnItemSelectedListener.OnItemSelectedCustom() {
            @Override
            public void onItemSelectedCustom(AdapterView<?> parent, View view, int position, long id, String spinnerType) {
                Integer abrigoSele = Integer.parseInt(parent.getItemAtPosition(position).toString());
                Double abrigoCalculado = abrigoSele * 9.5;
                viewModel.setPrendaField(spinnerType, abrigoCalculado);
            }
        },"abrigo"));
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
            tipoDePrendaSuperiorSpinner.setVisibility(esSuperior ? View.VISIBLE : View.GONE);
            if(!esSuperior) tipoDePrendaSuperiorSpinner.setSelection(0);
        }
        viewModel.setPrendaField(spinnerType, val);
    }
}
