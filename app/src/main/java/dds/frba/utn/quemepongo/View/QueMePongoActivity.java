package dds.frba.utn.quemepongo.View;

import android.app.Activity;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import dds.frba.utn.quemepongo.R;
import dds.frba.utn.quemepongo.View.Fragments.ProgressFragment;

public abstract class QueMePongoActivity extends AppCompatActivity {
    private Toolbar toolbar;
    protected QueMePongoActivity _activity = this;
    protected View currentView;
    private ProgressFragment progressFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        currentView = ((ViewGroup) _activity.findViewById(android.R.id.content)).getChildAt(0);

        toolbar = currentView.findViewById(R.id.toolbar);
        if(toolbar != null){
            setSupportActionBar(toolbar);

        }
        progressFragment = new ProgressFragment();
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
                Toast.makeText(_activity, "", Toast.LENGTH_SHORT).show();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    protected void setProgressDialog(Boolean show){
        if(show)
            showDialog();
        else
            hideDialog();
    }

    private void showDialog(){
        FragmentTransaction transaction = _activity.getSupportFragmentManager().beginTransaction();
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        transaction.add(android.R.id.content,progressFragment,"FRAGMENT_LOADER").addToBackStack(null).commit();
    }
    private void hideDialog(){
        progressFragment.dismiss();
    }


}
