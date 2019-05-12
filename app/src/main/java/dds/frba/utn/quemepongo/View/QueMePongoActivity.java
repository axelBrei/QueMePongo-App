package dds.frba.utn.quemepongo.View;

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
import dds.frba.utn.quemepongo.QueMePongo;
import dds.frba.utn.quemepongo.R;
import dds.frba.utn.quemepongo.View.Activity.LoginActivity;
import dds.frba.utn.quemepongo.View.Toolbar.ToolbarView;

public abstract class QueMePongoActivity extends AppCompatActivity {
    private ToolbarView toolbar;
    protected QueMePongoActivity _activity = this;
    private QueMePongo application;
    private ProgressBar progressBar;

    // MANDATORY METHODS
    protected abstract int getView();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getView());
        application = ( (QueMePongo) getApplication());

        application.loading.observe(_activity, aBoolean -> setProgressDialog(aBoolean));

        toolbar = new ToolbarView(_activity, enableToolbarSpinner());
        progressBar = findViewById(R.id.toolbarProgres);

    }

    public void enableBackButton(){
        toolbar.enableBackButton();
    }
    protected boolean enableToolbarSpinner(){
        return true;
    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if(toolbar != null){
            getMenuInflater().inflate(R.menu.main_menu, menu);
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.MainMenuLogout:{
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(_activity, LoginActivity.class);
                startActivity(intent);
                finish();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    protected void setProgressDialog(Boolean show){
        progressBar.setIndeterminate(show);
        if(progressBar != null && show)
            progressBar.setVisibility(View.VISIBLE);
        else
            progressBar.setVisibility(View.INVISIBLE);
    }
}
