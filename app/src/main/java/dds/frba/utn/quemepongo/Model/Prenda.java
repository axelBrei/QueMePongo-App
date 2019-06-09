package dds.frba.utn.quemepongo.Model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import dds.frba.utn.quemepongo.Utils.JsonParser.PrendaDeserializer;
import dds.frba.utn.quemepongo.Utils.JsonParser.PrendaSerializer;

@JsonSerialize(using = PrendaSerializer.class)
@JsonDeserialize(using = PrendaDeserializer.class)
public class Prenda {

    private Integer id = null;
    private String tipoDeTela;
    private String descripcion = "";
    private String colorP = "";
    private String colorS = "";
    // EG. Camprera Remera
    private String tipoDePrenda = "";

    public Prenda() {
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
