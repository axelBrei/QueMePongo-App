package dds.frba.utn.quemepongo.View.Activity;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
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
import dds.frba.utn.quemepongo.View.Fragments.PrendasFragment;
import dds.frba.utn.quemepongo.View.QueMePongoActivity;
import dds.frba.utn.quemepongo.ViewModel.PrendasViewModel;
import retrofit2.Call;
import retrofit2.Response;

public class MainActivity extends QueMePongoActivity  implements PrendasFragment.EventsInterface{
    // UI
//    private ConstraintLayout container;
    private PrendasFragment prendasFragment;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //BUSCO UI
//        container = findViewById(R.id.MainScreenContainer);

        // CREO SPINNER EN TOOLBAR
        setToolbarSpinner(true);

        prendasFragment = PrendasFragment.newInstance(this);

        //CARGO EL FRAGMENT
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.MainScreenContainer, prendasFragment)
                .addToBackStack(null)
                .commit();

    }

    @Override
    protected int getView() {
        return R.layout.activity_main;
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
    }

    @Override
    public void onLoading(Boolean isLoading) {
        setProgressDialog(isLoading);
    }

    @Override
    public void setSpinnerItem(int index) {

    }
}
