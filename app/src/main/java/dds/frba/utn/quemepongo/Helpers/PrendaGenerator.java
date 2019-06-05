package dds.frba.utn.quemepongo.Helpers;

import java.lang.reflect.Field;
import java.util.HashMap;

import dds.frba.utn.quemepongo.Model.Prenda;
import dds.frba.utn.quemepongo.Model.TiposPrenda.Superior;
import static dds.frba.utn.quemepongo.ViewModel.CrearPrendasViewModel.*;

public class PrendaGenerator {
    public static Prenda generarPrenda(HashMap<String, Object> prendaMap, int id){
        Prenda p;
        try {
            //CREO EL TIPO DE PRENDA EN BASE AL NOMBRE
            Class<?> prendaClass = Class.forName("dds.frba.utn.quemepongo.Model.TiposPrenda." + prendaMap.get(TIPO_PARTE_QUE_OCUPA).toString());
            p = (Prenda) prendaClass.newInstance();
            // ASIGNO ATRIBUTOS A LA PRENDA
            for (Field f : Prenda.class.getDeclaredFields()) {
                Object val = prendaMap.get(f.getName());
                if(val == null) continue;
                if(!f.isAccessible()){
                    f.setAccessible(true);
                }
                f.set(p, val.toString());
            }
            if(prendaClass.equals(Superior.class)){
                ((Superior)p).setIdTipo((Integer)prendaMap.get(TIPO_PRENDA_SUPERIOR));
            }
            p.setId(id);
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
