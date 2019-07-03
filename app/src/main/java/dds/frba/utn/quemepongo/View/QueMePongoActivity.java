package dds.frba.utn.quemepongo.View;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import com.chivorn.smartmaterialspinner.SmartMaterialSpinner;
import com.google.firebase.auth.FirebaseAuth;
import com.jaredrummler.materialspinner.MaterialSpinner;

import java.util.ArrayList;
import java.util.List;

import dds.frba.utn.quemepongo.Adapters.SpinnerArrayAdapter;
import dds.frba.utn.quemepongo.Model.Guardarropa;
import dds.frba.utn.quemepongo.Model.Schedulable;
import dds.frba.utn.quemepongo.QueMePongo;
import dds.frba.utn.quemepongo.R;
import dds.frba.utn.quemepongo.View.Activity.LoginActivity;
import dds.frba.utn.quemepongo.View.Toolbar.ToolbarView;

public abstract class QueMePongoActivity extends AppCompatActivity implements Schedulable {
    private ToolbarView toolbar;
    protected QueMePongoActivity _activity = this;
    private QueMePongo application;
    private ProgressBar progressBar;
    private FirebaseAuth mAuth;

    // MANDATORY METHODS
    protected abstract int getView();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getView());
        application = ( (QueMePongo) getApplication());


        /**/
        mAuth = FirebaseAuth.getInstance();
        if (mAuth.getCurrentUser() != null) {
            application.loading.observe(_activity, aBoolean -> setProgressDialog(aBoolean));

            toolbar = new ToolbarView(_activity, enableToolbarSpinner());
            progressBar = findViewById(R.id.toolbarProgres);
        }
        /*

        */

    }

    public void enableBackButton(){
        toolbar.enableBackButton();
    }

    private void initSpinner() {
        AppCompatSpinner spinner = findViewById(R.id.toolbarSpinner);
        spinner.setPrompt("Guardarropas");
        SpinnerArrayAdapter<Guardarropa> adapter =
                new SpinnerArrayAdapter<Guardarropa>(_activity, android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ((TextView) parent.getChildAt(0)).setTextColor(Color.WHITE);
                application.setGuardarropaActual((Guardarropa) parent.getItemAtPosition(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ((QueMePongo)getApplication()).getGuardarropaObserver().observe(_activity, guardarropasList -> {
            adapter.addAll(guardarropasList);
            adapter.notifyDataSetChanged();
        });
    }
    protected boolean enableToolbarSpinner(){
        return true;
    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }




    protected void logout(){
        Intent intent = new Intent(_activity, LoginActivity.class);
        startActivity(intent);
        FirebaseAuth.getInstance().signOut();
        finish();
    }

    public void setProgressDialog(Boolean show){
        progressBar.setIndeterminate(show);
        if(progressBar != null && show)
            progressBar.setVisibility(View.VISIBLE);
        else
            progressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void startLoading() {
        setProgressDialog(true);
    }

    @Override
    public void stopLoading() {
        setProgressDialog(false);
    }

    @Override
    public Context getContext() {
        return _activity;
    }
}
