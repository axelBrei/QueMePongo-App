package dds.frba.utn.quemepongo.Model;

import java.util.ArrayList;
import java.util.List;

public class Cliente {
    private List<Guardarropa> guardarropas;

    public Cliente() {
        this.guardarropas = new ArrayList<>();
    }

    public List<Guardarropa> getGuardarropas() {
        return guardarropas;
    }

    public void setGuardarropas(List<Guardarropa> guardarropas) {
        this.guardarropas = guardarropas;
    }

    public void addGuardarropa(Guardarropa g){
        guardarropas.add(g);
    }

    public void anadirPrendaAlGuardarropa(Prenda prenda, Guardarropa guardarropa){
        guardarropas.get(guardarropas.indexOf(guardarropa)).aniadirPrenda(prenda);
    }
}
