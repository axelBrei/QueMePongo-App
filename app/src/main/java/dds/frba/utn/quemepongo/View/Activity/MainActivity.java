package dds.frba.utn.quemepongo.View.Activity;

import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.List;

import dds.frba.utn.quemepongo.Adapters.PrendasAdapter;
import dds.frba.utn.quemepongo.Helpers.RetrofitInstanciator;
import dds.frba.utn.quemepongo.Model.Prenda;
import dds.frba.utn.quemepongo.R;
import dds.frba.utn.quemepongo.Utils.JsonParser.PrendasContainer;
import dds.frba.utn.quemepongo.Utils.PrendasJsonParser;
import dds.frba.utn.quemepongo.View.QueMePongoActivity;
import dds.frba.utn.quemepongo.ViewModel.PrendasViewModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends QueMePongoActivity {
    private PrendasViewModel prendasViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        // IMPORTANTE: SIEMPRE PONER EL SETCONTENTVIEW ANTES DEL SUPER PARA QUE FUNCIONE QUEMEPONGOACTIVITY
        setContentView(R.layout.activity_main);
        super.onCreate(savedInstanceState);


        RecyclerView prendasRecyclerView = findViewById(R.id.MainScreenRecyclerView);


        prendasViewModel = ViewModelProviders.of(_activity).get(PrendasViewModel.class);
        PrendasAdapter adapter = new PrendasAdapter(/*PrendasJsonParser.getJsonPrendasJson(_activity),*/_activity);

        prendasRecyclerView.setAdapter(adapter);
        prendasRecyclerView.setLayoutManager(new LinearLayoutManager(_activity, LinearLayout.VERTICAL,false));

        prendasViewModel.getPrendas().observe(_activity, prendas -> adapter.addItems(prendas));
//        prendasViewModel.setPrendas(PrendasJsonParser.getJsonPrendasJson(_activity));
        fetchPrendas();
    }

    private void fetchPrendas(){
        setProgressDialog(true);
        prendasViewModel.getPrendasRepository().getPrendas().enqueue(new Callback<PrendasContainer>() {
            @Override
            public void onResponse(Call<PrendasContainer> call, Response<PrendasContainer> response) {
                prendasViewModel.setPrendas(response.body().getPrendaslist());
                setProgressDialog(false);
            }

            @Override
            public void onFailure(Call<PrendasContainer> call, Throwable t) {
                setProgressDialog(false);
                t.printStackTrace();
                Toast.makeText(_activity, "Error", Toast.LENGTH_SHORT).show();
            }
        });
    }


}
