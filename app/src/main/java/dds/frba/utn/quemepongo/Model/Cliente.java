package dds.frba.utn.quemepongo.Model;

import java.util.ArrayList;
import java.util.List;

public class Cliente {
    private String uid;
    private String mail;
    private String name;

    private List<Guardarropa> guardarropas;

    public Cliente() {
        this.guardarropas = new ArrayList<>();
    }

    public Cliente(String uid, String mail, String name) {
        this.uid = uid;
        this.mail = mail;
        this.name = name;
        this.guardarropas = new ArrayList<>();
    }

    public Cliente(String mail, String name) {
        this.mail = mail;
        this.name = name;
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
