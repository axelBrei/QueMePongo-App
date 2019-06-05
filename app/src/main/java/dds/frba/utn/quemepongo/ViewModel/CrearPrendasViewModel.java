package dds.frba.utn.quemepongo.ViewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.content.Intent;
import android.support.annotation.NonNull;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import dds.frba.utn.quemepongo.Helpers.PrendaGenerator;
import dds.frba.utn.quemepongo.Helpers.RetrofitInstanciator;
import dds.frba.utn.quemepongo.Model.Prenda;
import dds.frba.utn.quemepongo.Model.TiposPrenda.Superior;
import dds.frba.utn.quemepongo.QueMePongo;
import dds.frba.utn.quemepongo.Repository.PrendasRepository;

public class CrearPrendasViewModel extends AndroidViewModel {
    // STATIC CONSTANTS
    public static final String PRIMARY_COLOR_TYPE = "colorP";
    public static final String SECONDARY_COLOR_TYPE = "colorS";
    public static final String TIPO_DE_TELA = "tipoDeTela";
    public static final String TIPO_PARTE_QUE_OCUPA = "parteQueOcupa";
    public static final String DESCRIPCION_PRENDA = "descripcion";
    public static final String TIPO_PRENDA_SUPERIOR = "TipoSuperior";
    public static final String PRENDA_SUPERIOR_REMERA = "Remera";
    public static final String PRENDA_SUPERIOR_BUZO = "Buzo";
    public static final String PRENDA_SUPERIOR_CAMPERA = "Campera";
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
        return Arrays.asList("Superior", "Inferior", "Accesorios", "Calzado");
    }

    public List<String> getTiposSuperiores() {
        return Arrays.asList(PRENDA_SUPERIOR_REMERA, PRENDA_SUPERIOR_BUZO, PRENDA_SUPERIOR_CAMPERA);
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

    public Prenda getPrendaGenerada(int id){
        return PrendaGenerator.generarPrenda(prenda, id);
    }

    public QueMePongo getApplication(){
        return (QueMePongo) super.getApplication();
    }
}
