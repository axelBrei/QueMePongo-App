package dds.frba.utn.quemepongo.View.Activity;

import android.arch.lifecycle.ViewModelProviders;
import android.support.design.button.MaterialButton;
import android.support.design.widget.TextInputEditText;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.chivorn.smartmaterialspinner.SmartMaterialSpinner;
import com.google.firebase.auth.FirebaseAuth;

import java.util.HashMap;

import dds.frba.utn.quemepongo.Controllers.JsonController;
import dds.frba.utn.quemepongo.Helpers.CustomRetrofitCallback;
import dds.frba.utn.quemepongo.Model.WebServices.Response.Prendas.AddPrendaResponse;
import dds.frba.utn.quemepongo.QueMePongo;
import dds.frba.utn.quemepongo.R;
import dds.frba.utn.quemepongo.Utils.CustomOnItemSelectedListener;
import dds.frba.utn.quemepongo.View.QueMePongoActivity;
import dds.frba.utn.quemepongo.ViewModel.CrearPrendasViewModel;
import retrofit2.Call;
import retrofit2.Response;

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

        initUI();

    }

    @Override
    protected int getView() {
        return R.layout.activity_crear_prendas;
    }

    private void initUI(){
        uploadButton.setOnClickListener( (v) -> {
            setProgressDialog(true);
            viewModel.setPrendaField(CrearPrendasViewModel.DESCRIPCION_PRENDA, descripcionEditText.getText().toString());
            HashMap<String, Object> request = new HashMap<>();
            request.put("prenda", viewModel.getPrenda());
            request.put("uid", FirebaseAuth.getInstance().getCurrentUser().getUid());
            request.put("idGuardarropa", ( (QueMePongo)viewModel.getApplication()).getGuardarropaActual().getValue().getId());
            Call<HashMap<Object, Object>> c = viewModel.getPrendasRepository().anadirPrenda(request);
            c.enqueue(new CustomRetrofitCallback<HashMap<Object, Object>>() {
                @Override
                public void onCustomResponse(Call<HashMap<Object, Object>> call, Response<HashMap<Object, Object>> response) {
                    setProgressDialog(false);
                    Integer prendaId = Float.floatToIntBits(Float.parseFloat(response.body().get("idPrenda").toString()));
                    viewModel.getApplication().addPrendaToGuardarropa(viewModel.getPrendaGenerada(prendaId));
                    onBackPressed();
                }

                @Override
                public void onCustomFailure(Call<HashMap<Object, Object>> call, Error error) {
                    Toast.makeText(_activity, error.getMessage(), Toast.LENGTH_SHORT).show();
                    setProgressDialog(false);
                }

                @Override
                public void onHttpRequestFail(Call<HashMap<Object, Object>> call, Throwable t) {
                    Toast.makeText(_activity, "Ha ocurrido un error inesperado", Toast.LENGTH_SHORT).show();
                    setProgressDialog(false);
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
