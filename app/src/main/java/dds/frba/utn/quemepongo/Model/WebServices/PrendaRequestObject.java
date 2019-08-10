package dds.frba.utn.quemepongo.Model.WebServices;

import dds.frba.utn.quemepongo.Model.Prenda;

public class PrendaRequestObject {
    private String parteQueOcupa;
    private Integer id = null;
    private String tipoDeTela = "";
    private String descripcion = "";
    private String colorP = "";
    private String colorS = "";
    // EG. Camprera Remera
    private String tipoDePrenda = "";

    public PrendaRequestObject(Prenda prenda) {
        this.parteQueOcupa = prenda.getTipoDePrenda();
        this.id = prenda.getId();
        this.tipoDePrenda = prenda.getTipoDePrenda();
        this.descripcion = prenda.getDescripcion();
        this.colorP = prenda.getColorP();
        this.colorS = prenda.getColorS();
        this.tipoDeTela = prenda.getTipoDeTela();
    }

    public PrendaRequestObject() {
    }

    public String getParteQueOcupa() {
        return parteQueOcupa;
    }

    public void setParteQueOcupa(String parteQueOcupa) {
        this.parteQueOcupa = parteQueOcupa;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTipoDeTela() {
        return tipoDeTela;
    }

    public void setTipoDeTela(String tipoDeTela) {
        this.tipoDeTela = tipoDeTela;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getColorP() {
        return colorP;
    }

    public void setColorP(String colorP) {
        this.colorP = colorP;
    }

    public String getColorS() {
        return colorS;
    }

    public void setColorS(String colorS) {
        this.colorS = colorS;
    }

    public String getTipoDePrenda() {
        return tipoDePrenda;
    }

    public void setTipoDePrenda(String tipoDePrenda) {
        this.tipoDePrenda = tipoDePrenda;
    }
}
