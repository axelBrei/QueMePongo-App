package dds.frba.utn.quemepongo.View.Fragments;


import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dd.processbutton.iml.ActionProcessButton;

import dds.frba.utn.quemepongo.Helpers.CustomRetrofitCallback;
import dds.frba.utn.quemepongo.Model.WebServices.Error;
import dds.frba.utn.quemepongo.QueMePongo;
import dds.frba.utn.quemepongo.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


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
        ActionProcessButton button = view.findViewById(R.id.ErrorFragmentretryButton);
        button.setOnClickListener( (View v) -> {
            button.setProgress(50);
            call.clone().enqueue(new CustomRetrofitCallback() {
                @Override
                public void onCustomResponse(Call call, Response response) {
                    getActivity().getFragmentManager().beginTransaction().remove(ErrorFragment.this).commit();
                    callback.onCustomResponse(call, response);
                }

                @Override
                public void onCustomFailure(Call call, Error error) {
                    callback.onCustomFailure(call, error);
                }

                @Override
                public void onHttpRequestFail(Call call, Throwable t) {
                    callback.onHttpRequestFail(call, t);
                }
            });
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
