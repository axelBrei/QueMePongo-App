package dds.frba.utn.quemepongo.View.Fragments;


import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dd.processbutton.iml.ActionProcessButton;

import dds.frba.utn.quemepongo.Helpers.CustomRetrofitCallback;
import dds.frba.utn.quemepongo.QueMePongo;
import dds.frba.utn.quemepongo.R;
import retrofit2.Call;
import retrofit2.Callback;


public class ErrorFragment extends Fragment {
    private Call call;
    private CustomRetrofitCallback callback;


    public ErrorFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_error, container, false);
        // TODO: crear contenido de la pantalla
        ActionProcessButton button = view.findViewById(R.id.ErrorFragmentretryButton);
        button.setOnClickListener( (View v) -> {
            button.setProgress(50);
            call.clone().enqueue(callback);
        });

        return  view;
    }

    public void setCall(Call call){
        this.call = call;
    }
    public void setCallback(CustomRetrofitCallback callback){
        this.callback = callback;
    }
}
