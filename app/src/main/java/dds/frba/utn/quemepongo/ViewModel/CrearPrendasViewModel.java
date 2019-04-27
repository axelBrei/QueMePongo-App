package dds.frba.utn.quemepongo.ViewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.support.annotation.NonNull;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import dds.frba.utn.quemepongo.Helpers.RetrofitInstanciator;
import dds.frba.utn.quemepongo.Model.Prenda;
import dds.frba.utn.quemepongo.Repository.PrendasRepository;

public class CrearPrendasViewModel extends AndroidViewModel {
    // STATIC CONSTANTS
    public static final String PRIMARY_COLOR_TYPE = "colorP";
    public static final String SECONDARY_COLOR_TYPE = "colorS";
    public static final String TIPO_DE_TELA = "tipoDeTela";
    public static final String TIPO_PARTE_QUE_OCUPA = "parteQueOcupa";
    public static final String DESCRIPCION_PRENDA = "descripcion";
    public static final String ID_PRENDA = "id";

    // STATIC DATA
    private List<String> colores;
    private List<String> tiposDeTela;
    // DATA
    private HashMap<String, Object> prenda;
    // REPOSITORY
    private PrendasRepository prendasRepository;

    public CrearPrendasViewModel(@NonNull Application application) {
        super(application);
        prenda = new HashMap<String, Object>();
        prendasRepository = RetrofitInstanciator
                .getInstance()
                .getRetrofit()
                .create(PrendasRepository.class);
    }

    public List<String> getColores() {
        return colores;
    }

    public void setColores(List colores) {
        this.colores = colores;
    }

    public List<String> getTiposDeTela() {
        return tiposDeTela;
    }

    public void setTiposDeTela(List tiposDeTela) {
        this.tiposDeTela = tiposDeTela;
    }

    public List<String> getPartesQueOcupa() {
        return Arrays.asList("PrendaSuperior", "PrendaInferior", "Accesorios", "Calzado");
    }

    public HashMap<String, Object> getPrenda() {
        return prenda;
    }

    public void setPrenda(HashMap<String, Object> prenda) {
        this.prenda = prenda;
    }

    public void setPrendaField(String field, String value) {
        prenda.put(field, value);
    }

    public PrendasRepository getPrendasRepository() {
        return prendasRepository;
    }

    public Prenda getPrendaGenerada(){
//        Field field;
        Prenda p;
        try {
            //CREO EL TIPO DE PRENDA EN BASE AL NOMBRE
            Class<?> prendaClass = Class.forName("dds.frba.utn.quemepongo.Model.TiposPrenda." + prenda.get(TIPO_PARTE_QUE_OCUPA).toString());
            p = (Prenda) prendaClass.newInstance();
            // ASIGNO ATRIBUTOS A LA PRENDA
            for (Field f : Prenda.class.getDeclaredFields()) {
                Object val = prenda.get(f.getName());
                if(val == null) continue;
                if(!f.isAccessible()){
                    f.setAccessible(true);
                }
                f.set(p, val.toString());
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
