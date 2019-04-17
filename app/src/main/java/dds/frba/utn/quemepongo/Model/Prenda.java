package dds.frba.utn.quemepongo.Model;

public class Prenda {

    private Integer id;
    private TipoDeTela tipoDeTela;
    private String descripcion;
    private String colorP;
    private String colorS;


    public Prenda() {
    }

    public Prenda(Integer id, TipoDeTela tipoDeTela, String descripcion, String colorP, String colorS) {
        this.id = id;
        this.tipoDeTela = tipoDeTela;
        this.descripcion = descripcion;
        this.colorP = colorP;
        this.colorS = colorS;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public TipoDeTela getTipoDeTela() {
        return tipoDeTela;
    }

    public void setTipoDeTela(TipoDeTela tipoDeTela) {
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

}
