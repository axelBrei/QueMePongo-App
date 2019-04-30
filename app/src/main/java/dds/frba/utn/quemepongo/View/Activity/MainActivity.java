package dds.frba.utn.quemepongo.View.Activity;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;


import dds.frba.utn.quemepongo.Adapters.PrendasAdapter;
import dds.frba.utn.quemepongo.Helpers.CustomRetrofitCallback;
import dds.frba.utn.quemepongo.Model.WebServices.Request.Prendas.GetPrendasRequest;
import dds.frba.utn.quemepongo.R;
import dds.frba.utn.quemepongo.Utils.JsonParser.PrendasContainer;
import dds.frba.utn.quemepongo.View.QueMePongoActivity;
import dds.frba.utn.quemepongo.ViewModel.PrendasViewModel;
import retrofit2.Call;
import retrofit2.Response;

public class MainActivity extends QueMePongoActivity {
    // MODEL
    private PrendasViewModel prendasViewModel;
    // UI
    private TextView tienePrendasEditText;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        RecyclerView prendasRecyclerView = findViewById(R.id.MainScreenRecyclerView);
        FloatingActionButton agregarPrendaFAB = findViewById(R.id.MainScreenFloatinActionButton);
        tienePrendasEditText = findViewById(R.id.MainActivityTienePrendas);

        prendasViewModel = ViewModelProviders.of(_activity).get(PrendasViewModel.class);
        // PREPARO LISTA PARA QUE SE MUESTRE EN LA PANTALLA
        PrendasAdapter adapter = new PrendasAdapter(_activity);
        prendasRecyclerView.setAdapter(adapter);
        prendasRecyclerView.setLayoutManager(new LinearLayoutManager(_activity, LinearLayout.VERTICAL,false));

        // CREO OBSERVERS PARA QUE SE LLENEN LAS LISTAS
        prendasViewModel.getApplication().getGuardarropaActual().observe(
                _activity,
                guardarropa -> {
                    setSpinnerItem(prendasViewModel.getApplication().getGuardarropas().indexOf(guardarropa));
                    prendasViewModel.setPrendas(guardarropa.getPrendas());
                }
        );
        prendasViewModel.getPrendas().observe(
                _activity,
                prendas -> adapter.addItems(prendas)
        );
        // CREO SPINNER EN TOOLBAR
        setToolbarSpinner(true);


        agregarPrendaFAB.setOnClickListener( (View v) -> {
            // CARGO FRAGMENT PARA CREAR PRENDA
            Intent intent = new Intent(this, CrearPrendasActivity.class);
            startActivity(intent);
        });
    }

    @Override
    protected int getView() {
        return R.layout.activity_main;
    }

    private void fetchPrendas(){
        setProgressDialog(true);
        GetPrendasRequest req = new GetPrendasRequest();
        req.setIdGuardarropa("0");
        req.setUid(FirebaseAuth.getInstance().getUid());
        prendasViewModel.getPrendasRepository().getPrendas(req).enqueue(new CustomRetrofitCallback<PrendasContainer>() {
            @Override
            public void onCustomResponse(Call<PrendasContainer> call, Response<PrendasContainer> response) {
                prendasViewModel.setPrendas(response.body().getPrendaslist());
                setProgressDialog(false);
            }

            @Override
            public void onCustomFailure(Call<PrendasContainer> call, CustomRetrofitCallback<PrendasContainer>.Error error) {
                if(error.getStatus() == 404){
                    tienePrendasEditText.setVisibility(View.VISIBLE);
                }
                setProgressDialog(false);
                setProgressDialog(false);
            }


            @Override
            public void onHttpRequestFail(Call<PrendasContainer> call, Throwable t) {
                setProgressDialog(false);
                Toast.makeText(_activity, "Error al buscar las prendas del servidor", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
    }
}
