package dds.frba.utn.quemepongo.Utils;

import android.support.annotation.Nullable;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import dds.frba.utn.quemepongo.Model.Atuendo;
import dds.frba.utn.quemepongo.Model.Prenda;
import dds.frba.utn.quemepongo.Utils.JsonParser.PrendasContainer;
import dds.frba.utn.quemepongo.Utils.JsonParser.SinglePrendaDeserializer;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class JsonConverterFactory extends Converter.Factory {

    @Nullable
    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
        if(type.equals(PrendasContainer.class)){
            return GsonConverterFactory.create(
                        new GsonBuilder().registerTypeAdapter(PrendasContainer.class, new PrendasJsonParser()).create())
                    .responseBodyConverter(type, annotations,retrofit);
        }else if(type.equals(Atuendo.class)){
            return  GsonConverterFactory.create(
                    new GsonBuilder().registerTypeAdapter(Atuendo.class, parserAtuendo()).create()
            ).responseBodyConverter(type,annotations, retrofit);
        }

        return GsonConverterFactory.create().responseBodyConverter(type,annotations,retrofit);
    }

    private JsonDeserializer<Atuendo> parserAtuendo(){
        return (json, typeOfT, context) -> new Atuendo(PrendasJsonParser.parsePrendas(json));
    }
}
