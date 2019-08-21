package dds.frba.utn.quemepongo.View.Activity;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import androidx.annotation.Nullable;

import com.google.firebase.auth.FirebaseAuth;

import dds.frba.utn.quemepongo.Adapters.GuardarropasAdapter;
import dds.frba.utn.quemepongo.Controllers.GuardarropaController;
import dds.frba.utn.quemepongo.Helpers.ListSwipeHelper;
import dds.frba.utn.quemepongo.Model.Guardarropa;
import dds.frba.utn.quemepongo.QueMePongo;
import dds.frba.utn.quemepongo.R;
import dds.frba.utn.quemepongo.Utils.ActivityHelper;
import dds.frba.utn.quemepongo.Utils.CustomListenners.OnCompleteListenerWithStatus;
import dds.frba.utn.quemepongo.Utils.CustomListenners.OnCompleteListenerWithStatusAndApplication;
import dds.frba.utn.quemepongo.Utils.Swipeable;
import dds.frba.utn.quemepongo.View.QueMePongoActivity;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import me.ydcool.lib.qrmodule.activity.QrScannerActivity;

@FieldDefaults(level =  AccessLevel.PRIVATE)
public class GuardarropasActivity extends QueMePongoActivity implements Swipeable {

    FloatingActionButton agregarFAB;
    RecyclerView guardarropasRecyclerView;
    GuardarropasAdapter adapter;
    GuardarropaController controller;
    Guardarropa guardarropaACompartir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        enableBackButton();
        setToolbarTitle("Guardarropas");
        controller = new GuardarropaController(_activity);

        agregarFAB = findViewById(R.id.GuardarropasCrearGuardarropaFAB);
        setFabClickListener();

        guardarropasRecyclerView = findViewById(R.id.GuardarropasRecyclerView);
        adapter = new GuardarropasAdapter(_activity, this);
        ((QueMePongo) getApplication()).getGuardarropas().observe(_activity, list -> {
            adapter.setGuardarropas(list);
        });
        guardarropasRecyclerView.setAdapter(adapter);
        guardarropasRecyclerView.setLayoutManager( new LinearLayoutManager(_activity, LinearLayoutManager.VERTICAL,false));
    }

    private void setFabClickListener() {
        agregarFAB.setOnClickListener( (v) -> ActivityHelper.startActivity(_activity, CrearGuardarropaActivity.class));
    }

    @Override
    protected int getView() {
        return R.layout.activity_guardarropas;
    }

    @Override
    protected boolean enableToolbarSpinner() {
        return false;
    }

    @Override
    public void onSwipeLeft(Guardarropa g) {
        // TODO: delete
        controller.borrarGuardarropas(g.getId(), new OnCompleteListenerWithStatusAndApplication() {
            @Override
            public void onComplete(Boolean success, QueMePongo application, @Nullable Object obj) {
                if(success){
                    adapter.removeItem(g);
                    application.deleteGuardarropa(g);
                }
            }
        });
    }

    @Override
    public void onSwipeRight(Guardarropa g) {
        guardarropaACompartir = g;
        ActivityHelper.startActivityForResult(_activity, QrScannerActivity.class,QrScannerActivity.QR_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @android.support.annotation.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == QrScannerActivity.QR_REQUEST_CODE && data != null){
            Bundle bundle = data.getExtras();
            if(bundle != null){
                String result = bundle.getString(QrScannerActivity.QR_RESULT_STR, "");
                if(!result.isEmpty()){
                    // TODO: share guardarropa
                    controller.compartirGuardarropa(
                            guardarropaACompartir.getId(),
                            result,
                            (succed, obj) -> {
                                if(succed)
                                    onBackPressed();
                            }
                    );
                }
            }
        }
        adapter.notifyDataSetChanged();
    }
}
