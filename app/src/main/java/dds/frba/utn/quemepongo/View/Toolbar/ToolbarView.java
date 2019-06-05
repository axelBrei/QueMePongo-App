package dds.frba.utn.quemepongo.View.Toolbar;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import dds.frba.utn.quemepongo.Adapters.SpinnerArrayAdapter;
import dds.frba.utn.quemepongo.Model.Guardarropa;
import dds.frba.utn.quemepongo.QueMePongo;
import dds.frba.utn.quemepongo.R;
import dds.frba.utn.quemepongo.View.QueMePongoActivity;

public class ToolbarView extends Toolbar {
    private View view;
    private AppCompatActivity activity;
    private QueMePongo application;
    private AppCompatSpinner spinner;
    private boolean spinnerEnabled;

    public ToolbarView(AppCompatActivity activity, boolean enableSpinner) {
        super(activity);
        application = ((QueMePongo) activity.getApplication());
        this.activity = activity;
        this.spinnerEnabled = enableSpinner;
        this.view = activity.findViewById(R.id.toolbar);
        spinner = view.findViewById(R.id.toolbarSpinner);
        if(spinnerEnabled)
            initSpinner();
        else
            spinner.setVisibility(GONE);

        activity.setSupportActionBar(activity.findViewById(R.id.toolbar));
        activity.getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    public static void setToolbarTitle(QueMePongoActivity activity, String title){
        activity.getSupportActionBar().setDisplayShowTitleEnabled(true);
        activity.getSupportActionBar().setTitle(title);
    }

    public int getToolbarId(){
        return R.id.toolbar;
    }

    private void initSpinner(){
        List<Guardarropa> guardarropas = application.getGuardarropas();
//        MaterialSpinner spinner = findViewById(R.id.toolbarSpinner);
        List<String> descripciones = new ArrayList<>();
        if(guardarropas == null || guardarropas.size() == 0) return;
        for (Guardarropa g :guardarropas) {
            descripciones.add(g.getDescripcion());
        }
        spinner.setPrompt("Guardarropas");
        SpinnerArrayAdapter<Guardarropa> adapter =
                new SpinnerArrayAdapter<Guardarropa>(activity, android.R.layout.simple_spinner_dropdown_item);
        adapter.addAll(guardarropas);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ((TextView) parent.getChildAt(0)).setTextColor(Color.WHITE);
                application.setGuardarropaActual( guardarropas.get(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void enableBackButton(){
        if(activity.getSupportActionBar() != null){
            activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            activity.getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }

    protected void setToolbarSpinner(Boolean showToolbar){
        findViewById(R.id.toolbarSpinner).setVisibility( showToolbar ? View.VISIBLE : View.GONE);
    }
}
