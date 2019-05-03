package dds.frba.utn.quemepongo.View;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.chivorn.smartmaterialspinner.SmartMaterialSpinner;
import com.google.firebase.auth.FirebaseAuth;
import com.jaredrummler.materialspinner.MaterialSpinner;

import java.util.ArrayList;
import java.util.List;

import dds.frba.utn.quemepongo.Model.Guardarropa;
import dds.frba.utn.quemepongo.QueMePongo;
import dds.frba.utn.quemepongo.R;
import dds.frba.utn.quemepongo.View.Activity.LoginActivity;

public abstract class QueMePongoActivity extends AppCompatActivity {
    private Toolbar toolbar;
    protected QueMePongoActivity _activity = this;
    protected View currentView;
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

        toolbar = findViewById(R.id.toolbar);
        progressBar = findViewById(R.id.toolbarProgres);
        if(toolbar != null){
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

        initSpinner();
    }

    public void enableBackButton(){
        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }

    private void initSpinner(){
        List<Guardarropa> guardarropas = ( (QueMePongo) getApplication()).getGuardarropas();
        MaterialSpinner spinner = findViewById(R.id.toolbarSpinner);
        List<String> descripciones = new ArrayList<>();
        if(guardarropas == null || guardarropas.size() == 0) return;
        for (Guardarropa g :guardarropas) {
            descripciones.add(g.getDescripcion());
        }
        spinner.setItems(descripciones);
        spinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                application.setGuardarropaActual( guardarropas.get(position));
            }
        });
        application.setGuardarropaActual(guardarropas.get(0));
        spinner.setSelectedIndex(0);
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

    protected void setToolbarSpinner(Boolean showToolbar){
        findViewById(R.id.toolbarSpinner).setVisibility( showToolbar ? View.VISIBLE : View.GONE);
    }

    protected void setSpinnerItem(int index){

    }

}
