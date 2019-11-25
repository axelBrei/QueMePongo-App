package dds.frba.utn.quemepongo.Helpers;

import android.app.Activity;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

import dds.frba.utn.quemepongo.Model.WebServices.Error;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public abstract class CustomRetrofitCallback<T> implements Callback<T> {

    @Override
    public void onResponse(Call<T> call, Response<T> response) {
        if(response.code() == 200){
            onCustomResponse(call,response);
        }else if(response.code() != 500 && response.errorBody() != null){
            try{
                ObjectMapper mapper = new ObjectMapper();
                Error e = mapper.readValue(response.errorBody().string(),Error.class);
                onCustomFailure(call, e);
            } catch (IOException e) {
                onHttpRequestFail(call, e);
            }
        }else{
            onHttpRequestFail(call, new Throwable());
        }
    }

    @Override
    public void onFailure(Call<T> call, Throwable t) {
        onHttpRequestFail(call,t);
    }

    public abstract void onCustomResponse(Call<T> call, Response<T> response);
    public abstract void onCustomFailure(Call<T> call, Error error);
    public abstract void onHttpRequestFail(Call<T> call, Throwable t);

}
