package dds.frba.utn.quemepongo.Model;

public class TipoDeTela {

    public TipoDeTela() {
    }

    public TipoDeTela(String descripcion) {
        this.descripcion = descripcion;
    }

    private String descripcion;

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
