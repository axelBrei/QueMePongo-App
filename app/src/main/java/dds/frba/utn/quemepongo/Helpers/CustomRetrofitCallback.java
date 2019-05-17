package dds.frba.utn.quemepongo.Helpers;

import android.app.Activity;

import com.google.gson.Gson;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public abstract class CustomRetrofitCallback<T> implements Callback<T> {

    @Override
    public void onResponse(Call<T> call, Response<T> response) {
        if(response.code() == 200){
            onCustomResponse(call,response);
        }else if(response.code() == 404 && response.errorBody() != null){
            try{
                Gson gson = new Gson();
                Error e = gson.fromJson(response.errorBody().string(), Error.class);
                onCustomFailure(call, e);
            } catch (IOException e) {
                onHttpRequestFail(call, e);
            }
        }
    }

    @Override
    public void onFailure(Call<T> call, Throwable t) {
        onHttpRequestFail(call,t);
    }

    public abstract void onCustomResponse(Call<T> call, Response<T> response);
    public abstract void onCustomFailure(Call<T> call, Error error);
    public abstract void onHttpRequestFail(Call<T> call, Throwable t);

    public class Error {
        private String timestamp;
        private Integer status;
        private String error;
        private String message;
        private String path;

        public Error() {
        }

        public String getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(String timestamp) {
            this.timestamp = timestamp;
        }

        public Integer getStatus() {
            return status;
        }

        public void setStatus(Integer status) {
            this.status = status;
        }

        public String getError() {
            return error;
        }

        public void setError(String error) {
            this.error = error;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }
    }
}
