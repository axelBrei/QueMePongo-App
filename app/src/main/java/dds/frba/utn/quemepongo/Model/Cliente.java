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

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
