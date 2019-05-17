package dds.frba.utn.quemepongo.Helpers;

import android.content.Context;

import com.google.gson.Gson;

import java.io.IOException;

import dds.frba.utn.quemepongo.QueMePongo;
import dds.frba.utn.quemepongo.Utils.JsonConverterFactory;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitInstanciator {
    private static RetrofitInstanciator instance;
    private Retrofit retrofit;
    private static QueMePongo application;


    public static RetrofitInstanciator getInstance() {
        return instance;
    }


    public Retrofit getRetrofit() {
        return retrofit;
    }

    public static void initializeRetrofit(QueMePongo app){
        instance = new RetrofitInstanciator();
        application = app;
    }

    private RetrofitInstanciator() {
        retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.0.16:8080")
//                .baseUrl("http://192.168.0.9:8080")
                .addConverterFactory(new JsonConverterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

}
