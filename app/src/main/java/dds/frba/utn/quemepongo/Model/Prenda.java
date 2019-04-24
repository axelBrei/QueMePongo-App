package dds.frba.utn.quemepongo.Model;

public class Prenda {

    private String tipo;
    private Integer id;
    private String tipoDeTela;
    private String descripcion;
    private String colorP;
    private String colorS;
    // EG. Camprera Remera
    private String tipoDePrenda;

    public String getTipo(){
        return tipo;
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
