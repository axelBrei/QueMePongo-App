package dds.frba.utn.quemepongo.Utils.JsonParser;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class PrendaSerializer extends JsonSerializer {

    @Override
    public void serialize(Object o, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        List<Field> objectFields = getObjectAndParentField(o);
        HashMap<String, Object> prendaMap = new HashMap<>();
        objectFields.forEach( field -> {
            try {
                if(!field.isAccessible())
                    field.setAccessible(true);
                prendaMap.put(field.getName(), field.get(o));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        });
        prendaMap.put("parteQueOcupa", o.getClass().getSimpleName());
        jsonGenerator.writeObject(prendaMap);
    }

    public static List<Field> getObjectAndParentField(Object p){
        List<Field> fields = new ArrayList<>();
        fields.addAll(Arrays.asList(p.getClass().getDeclaredFields()));
        fields.addAll(Arrays.asList(p.getClass().getSuperclass().getDeclaredFields()));
        return fields;
    }
}
