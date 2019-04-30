package dds.frba.utn.quemepongo.Utils;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import dds.frba.utn.quemepongo.Model.Prenda;
import dds.frba.utn.quemepongo.Utils.JsonParser.PrendasContainer;
import dds.frba.utn.quemepongo.Utils.JsonParser.SinglePrendaDeserializer;

public class PrendasJsonParser implements JsonDeserializer<PrendasContainer> {

    @Override
    public PrendasContainer deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        Gson gson = new GsonBuilder().registerTypeAdapter(Prenda.class, new SinglePrendaDeserializer()).create();

        List<Prenda> prendas = new ArrayList<>();
        JsonObject arrayObject = json.getAsJsonObject();
        JsonArray array = arrayObject.getAsJsonArray("prendas");
        for (JsonElement item : array) {
            Prenda prenda = gson.fromJson(item, Prenda.class);
            if(prenda != null)
                prendas.add(prenda);
        }
        return new PrendasContainer(prendas);
    }



    public static List<Prenda> getJsonPrendasJson(Context context){
        try{
            InputStream stream = context.getAssets().open("data.json");

            int size = stream.available();
            byte[] buffer = new byte[size];
            stream.read(buffer);
//            FileReader fileReader = new FileReader("dds/frba/utn/quemepongo/Constants/data.json");
            Gson gson = new GsonBuilder().registerTypeAdapter(PrendasContainer.class, new PrendasJsonParser()).create();
            String json = new String(buffer);
            PrendasContainer prendasContainer = gson.fromJson(json, PrendasContainer.class);
            stream.close();
            return prendasContainer.getPrendaslist();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
