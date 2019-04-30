package dds.frba.utn.quemepongo.Utils.JsonParser;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

import dds.frba.utn.quemepongo.Model.Prenda;

public class SinglePrendaDeserializer implements JsonDeserializer<Prenda> {
    @Override
    public Prenda deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        try {
            // SE CREA EL TIPO DE LA PRENDA EN BASE AL NOMBRE DEL TIPO DE PRENDA
            JsonObject jsonObject = json.getAsJsonObject();
            String prendaType = json.getAsJsonObject().get("parteQueOcupa").toString().toString().replaceAll("\"","");
            Class<?> prendaClass  = Class.forName("dds.frba.utn.quemepongo.Model.TiposPrenda." + prendaType);
            Prenda prenda = (Prenda) prendaClass.newInstance();

            // SE ASIGNAN LOS ATRIBUTOS CORRESPONDIENTES
            if(jsonObject.has("id")){
                String stringId = jsonObject.get("id").toString().replaceAll("\"","");
                try{
                    prenda.setId( Integer.parseInt(stringId));
                }catch (NumberFormatException e){
                    prenda.setId(0);
                }
            }
            prenda.setDescripcion(jsonObject.get("descripcion").toString().replaceAll("\"",""));
            prenda.setColorP(jsonObject.get("colorP").toString().replaceAll("\"",""));
            prenda.setColorS(jsonObject.get("colorS").toString().replaceAll("\"",""));
            prenda.setTipoDeTela(jsonObject.get("tipoDeTela").toString().replaceAll("\"",""));
            return prenda;

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }

        return null;
    }
}
