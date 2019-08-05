package dds.frba.utn.quemepongo.View.Activity;

import android.Manifest;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.button.MaterialButton;
import android.support.design.widget.TextInputEditText;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.view.View;
import android.widget.AdapterView;

import com.chivorn.smartmaterialspinner.SmartMaterialSpinner;
import com.google.firebase.auth.FirebaseAuth;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import dds.frba.utn.quemepongo.Controllers.JsonController;
import dds.frba.utn.quemepongo.Helpers.ErrorHelper;
import dds.frba.utn.quemepongo.QueMePongo;
import dds.frba.utn.quemepongo.R;
import dds.frba.utn.quemepongo.Utils.CustomOnItemSelectedListener;
import dds.frba.utn.quemepongo.Utils.OnCompleteListenner;
import dds.frba.utn.quemepongo.View.QueMePongoActivity;
import dds.frba.utn.quemepongo.View.Toolbar.ToolbarView;
import dds.frba.utn.quemepongo.ViewModel.CrearPrendasViewModel;
import retrofit2.Call;

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
    private TextInputEditText descripcionEditText;
    private MaterialButton uploadButton;
    private MaterialButton cargarImagen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // BUSCO EN EL XML LOS COMPONENTES
        telasSpinner = findViewById(R.id.CrearPrendaTelasSpinner);
        primaryColorSpinner = findViewById(R.id.CrearPrendaPrimaryoloresSpiner);
        secondaryColorSpinner = findViewById(R.id.CrearPrendaSecondaryColoresSpiner);
        tipoDePrendaSpinner = findViewById(R.id.CrearPrendaParteQueOcupaSpinner);
        tipoDePrendaSuperiorSpinner = findViewById(R.id.CrearPrendaTipoSuperior);
        descripcionEditText = findViewById(R.id.CrearEventoDescripcion );
        uploadButton = findViewById(R.id.CrearPrendaUploadButton);
        cargarImagen = findViewById( R.id.CargarImagen );
        viewModel = ViewModelProviders.of(this).get(CrearPrendasViewModel.class);
        JsonController controller = new JsonController(this);
        viewModel.setColores(controller.getColors());
        viewModel.setTiposDeTela(controller.getTiposDeTela());
        if (ContextCompat.checkSelfPermission(CrearPrendasActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(CrearPrendasActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(CrearPrendasActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, 1000);
        }
        initUI();
        ToolbarView.setToolbarTitle(_activity, "Crear prenda");

    }
    protected void setCargarImagen(View view){

        Intent takePictureIntent = new Intent( MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,"com.example.android.fileprovider", photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI.toString());
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }
    String mCurrentPhotoPath;
    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "Backup_" + timeStamp + "_";
        File storageDir = getExternalFilesDir( Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(imageFileName, ".jpg", storageDir);

        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
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
        setSpinnerClickListeners();
    }

    private void setSpinnerClickListeners(){
        primaryColorSpinner.setOnItemSelectedListener(new CustomOnItemSelectedListener(this,PRIMARY_COLOR_TYPE));
        secondaryColorSpinner.setOnItemSelectedListener(new CustomOnItemSelectedListener(this,SECONDARY_COLOR_TYPE));
        telasSpinner.setOnItemSelectedListener(new CustomOnItemSelectedListener(this, TIPO_DE_TELA));
        tipoDePrendaSpinner.setOnItemSelectedListener(new CustomOnItemSelectedListener(this, TIPO_PARTE_QUE_OCUPA));
        tipoDePrendaSuperiorSpinner.setOnItemSelectedListener(new CustomOnItemSelectedListener(new CustomOnItemSelectedListener.OnItemSelectedCustom() {
            @Override
            public void onItemSelectedCustom(AdapterView<?> parent, View view, int position, long id, String spinnerType) {
                viewModel.setPrendaField(spinnerType, position);
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
    static final int REQUEST_TAKE_PHOTO = 1;
    public void tomarFoto(View view) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,"com.example.android.fileprovider", photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI.toString());
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }

}
