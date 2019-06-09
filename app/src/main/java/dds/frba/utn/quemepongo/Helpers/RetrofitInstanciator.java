package dds.frba.utn.quemepongo.Helpers;

import dds.frba.utn.quemepongo.QueMePongo;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

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
                .addConverterFactory(JacksonConverterFactory.create())
                .build();
    }

}
