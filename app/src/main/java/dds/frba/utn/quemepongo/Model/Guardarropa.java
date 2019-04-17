package dds.frba.utn.quemepongo.Model;

import java.util.ArrayList;
import java.util.List;

public class Guardarropa {
    private List<Prenda> prendas;

    public Guardarropa() {
        this.prendas = new ArrayList<>();
    }

    public List<Prenda> getPrendas() {
        return prendas;
    }

    public void setPrendas(List<Prenda> prendas) {
        this.prendas = prendas;
    }
}
