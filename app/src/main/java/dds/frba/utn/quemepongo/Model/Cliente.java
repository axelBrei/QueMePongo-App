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
}
