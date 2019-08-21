package dds.frba.utn.quemepongo.View;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ProgressBar;

import com.google.firebase.auth.FirebaseAuth;

import dds.frba.utn.quemepongo.Model.Schedulable;
import dds.frba.utn.quemepongo.QueMePongo;
import dds.frba.utn.quemepongo.R;
import dds.frba.utn.quemepongo.Utils.ActivityHelper;
import dds.frba.utn.quemepongo.View.Activity.LoginActivity;
import dds.frba.utn.quemepongo.View.Toolbar.ToolbarView;

public abstract class QueMePongoActivity extends AppCompatActivity implements Schedulable {
    public static final String ENABLE_BACK_BUTTON = "ENABLE_BACK_BUTTON";

    private ToolbarView toolbar;
    protected QueMePongoActivity _activity = this;
    private QueMePongo application;
    private ProgressBar progressBar;
    private FirebaseAuth mAuth;

    private Boolean backPressEnabled = true;

    // MANDATORY METHODS
    protected abstract int getView();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getView());
        application = ( (QueMePongo) getApplication());
        mAuth = FirebaseAuth.getInstance();
        if (mAuth.getCurrentUser() != null){
            toolbar = new ToolbarView(_activity, enableToolbarSpinner());
            if(toolbar.getView() != null){
                application.loading.observe(_activity, aBoolean -> setProgressDialog(aBoolean));
                progressBar = findViewById(R.id.toolbarProgres);
            }
        }
        retriveBundleData();
    }

    private void retriveBundleData(){
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if(bundle != null){
            backPressEnabled = bundle.getBoolean(ENABLE_BACK_BUTTON,true);
        }
    }

    public void enableBackButton(){
        toolbar.enableBackButton();
    }

    @Override
    public void onBackPressed() {
        if(backPressEnabled){
            super.onBackPressed();
        }
    }

    protected boolean enableToolbarSpinner(){
        return true;
    }
    protected void setToolbarTitle(String title){
        toolbar.setToolbarTitle(title);
    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    protected void logout(){
        ActivityHelper.startActivity(_activity, LoginActivity.class);
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
    public AppCompatActivity getContext() {
        return _activity;
    }
}
