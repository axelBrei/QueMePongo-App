package dds.frba.utn.quemepongo.Helpers;

import dds.frba.utn.quemepongo.Utils.JsonConverterFactory;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitInstanciator {
    private static final RetrofitInstanciator instance = new RetrofitInstanciator();
    private Retrofit retrofit;

    public static RetrofitInstanciator getInstance() {
        return instance;
    }

    public static <T> T getRetrofitClass(Class<T> repositoryClass) {
        return instance.retrofit.create(repositoryClass);
    }

    public Retrofit getRetrofit() {
        return retrofit;
    }

    private RetrofitInstanciator() {
        retrofit = new Retrofit.Builder()
//                .baseUrl("http://10.0.2.2:8080")
                .baseUrl("http://192.168.0.9:8080")
                .addConverterFactory(new JsonConverterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
}
