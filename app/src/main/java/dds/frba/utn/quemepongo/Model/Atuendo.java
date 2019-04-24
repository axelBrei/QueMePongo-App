package dds.frba.utn.quemepongo.Model;

import java.util.List;

public class Atuendo {
    private List<Prenda> prendas;

    public Atuendo(List<Prenda> prendas) {
        this.prendas = prendas;
    }

    public Atuendo() {
    }

    public void anadirPrenda(Prenda p){
        prendas.add(p);
    }

    public void eliminarPrenda(Prenda p){
        prendas.remove(p);
    }

    public List<Prenda> getPrendas() {
        return prendas;
    }

    public void setPrendas(List<Prenda> prendas) {
        this.prendas = prendas;
    }
}
