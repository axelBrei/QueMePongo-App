package dds.frba.utn.quemepongo.Utils.PrendasJsonDeserializer;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

import dds.frba.utn.quemepongo.Model.Prenda;
import dds.frba.utn.quemepongo.Model.TipoDeTela;

public class TipoDeTelaDeserializer implements JsonDeserializer<TipoDeTela> {

    @Override
    public TipoDeTela deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        try{
            JsonObject jsonObject = json.getAsJsonObject();
            String telaType = jsonObject.get("descripcion").getAsString();
            if(telaType == null)
                return null;
            Class<?>  telaClass  = Class.forName("dds.frba.utn.quemepongo.Model.TiposDeTela." + telaType);
            TipoDeTela tipoDeTela = (TipoDeTela) telaClass.newInstance();

            tipoDeTela.setDescripcion(telaType);
            return  tipoDeTela;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return null;
    }
}
