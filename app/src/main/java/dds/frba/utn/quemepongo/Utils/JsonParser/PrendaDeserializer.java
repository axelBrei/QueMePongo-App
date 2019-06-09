package dds.frba.utn.quemepongo.Utils.JsonParser;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;
import java.lang.reflect.Field;

import dds.frba.utn.quemepongo.Model.Prenda;

public class PrendaDeserializer extends JsonDeserializer<Prenda> {


    @Override
    public Prenda deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);
        try {
            Class<?> prendaClass = Class.forName("dds.frba.utn.quemepongo.Model.TiposPrenda." + node.get("parteQueOcupa").asText());
            Prenda p = (Prenda) prendaClass.newInstance();
            for (Field f :PrendaSerializer.getObjectAndParentField(p)) {
                try {
                    if(node.has(f.getName())) {
                        f.setAccessible(true);
                        JsonNode auxNode = node.get(f.getName());
                        if(auxNode.isInt()){
                            Integer num = auxNode.asInt();
                            f.set(p, num == 0 ? null : num);
                        }
                        if(auxNode.isTextual()){
                            String val = auxNode.asText();
                            f.set(p, val);
                        }
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }

            }
            return p;
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