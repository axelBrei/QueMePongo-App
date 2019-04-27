package dds.frba.utn.quemepongo.View.Activity;

import android.arch.lifecycle.ViewModelProviders;
import android.support.design.button.MaterialButton;
import android.support.design.widget.TextInputEditText;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import com.chivorn.smartmaterialspinner.SmartMaterialSpinner;

import dds.frba.utn.quemepongo.Controllers.JsonController;
import dds.frba.utn.quemepongo.Model.Prenda;
import dds.frba.utn.quemepongo.R;
import dds.frba.utn.quemepongo.Utils.CustomOnItemSelectedListener;
import dds.frba.utn.quemepongo.View.QueMePongoActivity;
import dds.frba.utn.quemepongo.ViewModel.CrearPrendasViewModel;

import static dds.frba.utn.quemepongo.ViewModel.CrearPrendasViewModel.PRIMARY_COLOR_TYPE;
import static dds.frba.utn.quemepongo.ViewModel.CrearPrendasViewModel.SECONDARY_COLOR_TYPE;
import static dds.frba.utn.quemepongo.ViewModel.CrearPrendasViewModel.TIPO_DE_TELA;
import static dds.frba.utn.quemepongo.ViewModel.CrearPrendasViewModel.TIPO_PARTE_QUE_OCUPA;

public class CrearPrendasActivity extends QueMePongoActivity implements CustomOnItemSelectedListener.OnItemSelectedCustom{
    // MODEL
    private CrearPrendasViewModel viewModel;
    // UI
    private SmartMaterialSpinner primaryColorSpinner;
    private SmartMaterialSpinner secondaryColorSpinner;
    private SmartMaterialSpinner telasSpinner;
    private SmartMaterialSpinner tipoDePrendaSpinner;
    private TextInputEditText descripcionEditText;
    private MaterialButton uploadButton;
    
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_crear_prendas);
        super.onCreate(savedInstanceState);

        // BUSCO EN EL XML LOS COMPONENTES
        telasSpinner = findViewById(R.id.CrearPrendaTelasSpinner);
        primaryColorSpinner = findViewById(R.id.CrearPrendaPrimaryoloresSpiner);
        secondaryColorSpinner = findViewById(R.id.CrearPrendaSecondaryColoresSpiner);
        tipoDePrendaSpinner = findViewById(R.id.CrearPrendaParteQueOcupaSpinner);
        descripcionEditText = findViewById(R.id.CrearPrendaDescripcion);
        uploadButton = findViewById(R.id.CrearPrendaUploadButton);

        viewModel = ViewModelProviders.of(this).get(CrearPrendasViewModel.class);
        JsonController controller = new JsonController(this);
        viewModel.setColores(controller.getColors());
        viewModel.setTiposDeTela(controller.getTiposDeTela());

        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayShowTitleEnabled(true);
            getSupportActionBar().setTitle("Crear prenda");
        }

        initUI();

    }

    private void initUI(){
        uploadButton.setOnClickListener( (v) -> {
            viewModel.setPrendaField(CrearPrendasViewModel.DESCRIPCION_PRENDA, descripcionEditText.getText().toString());
            Prenda prenda = viewModel.getPrendaGenerada();
        });

        initSpinners();
        // METODO QUE MUESTRA EL BACK BUTTON
        enableBackButton();
    }
    

    private void initSpinners(){
        primaryColorSpinner.setItems(viewModel.getColores());
        secondaryColorSpinner.setItems(viewModel.getColores());
        tipoDePrendaSpinner.setItems(viewModel.getPartesQueOcupa());
        telasSpinner.setItems(viewModel.getTiposDeTela());
        setSpinnerClickListeners();
    }

    private void setSpinnerClickListeners(){
        primaryColorSpinner.setOnItemSelectedListener(new CustomOnItemSelectedListener(this,PRIMARY_COLOR_TYPE));
        secondaryColorSpinner.setOnItemSelectedListener(new CustomOnItemSelectedListener(this,SECONDARY_COLOR_TYPE));
        telasSpinner.setOnItemSelectedListener(new CustomOnItemSelectedListener(this, TIPO_DE_TELA));
        tipoDePrendaSpinner.setOnItemSelectedListener(new CustomOnItemSelectedListener(this, TIPO_PARTE_QUE_OCUPA));
    }

    @Override
    public void onItemSelectedCustom(AdapterView<?> parent, View view, int position, long id, String spinnerType) {
        String val = parent.getItemAtPosition(position).toString();
        viewModel.setPrendaField(spinnerType, val);
    }
}
