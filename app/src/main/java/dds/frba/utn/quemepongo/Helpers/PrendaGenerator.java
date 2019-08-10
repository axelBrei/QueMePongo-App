package dds.frba.utn.quemepongo.Helpers;

import java.lang.reflect.Field;
import java.util.HashMap;

import dds.frba.utn.quemepongo.Model.Prenda;
import static dds.frba.utn.quemepongo.ViewModel.CrearPrendasViewModel.*;

public class PrendaGenerator {
    public static Prenda generarPrenda(HashMap<String, Object> prendaMap, int id){
        Prenda p;
        try {
            p = new Prenda();
            // ASIGNO ATRIBUTOS A LA PRENDA
            for (Field f : Prenda.class.getDeclaredFields()) {
                Object val = prendaMap.get(f.getName());
                if(val == null) continue;
                if(!f.isAccessible()){
                    f.setAccessible(true);
                }
                f.set(p, val);
            }
            p.setId(id);
            return p;

        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }
}
