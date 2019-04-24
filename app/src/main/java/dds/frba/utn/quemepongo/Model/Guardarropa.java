package dds.frba.utn.quemepongo.Model;

import java.util.ArrayList;
import java.util.List;

public class Guardarropa {
    private String descripcion;
    private int id;
    private List<Prenda> prendas ;

    public void setDescripcion(String unaDescripcion){
        descripcion = unaDescripcion;
    }


    public Guardarropa() {
        this.prendas = new ArrayList<>();
    }

    public List<Prenda> getPrendas() {
        return prendas;
    }

    public void setPrendas(List<Prenda> prendas) {
        this.prendas = prendas;
    }

    void eliminarPrenda(Prenda unaPrenda){
        prendas.remove(unaPrenda);
    }

    public void aniadirPrenda(Prenda unaPrenda) {
        prendas.add(unaPrenda);
    }

    public Atuendo generarAtuendo(){

        //ALGORITMO PARA GENERAR ATUENDO;
        Atuendo atuendo = new Atuendo();
        return atuendo;
    }
}
