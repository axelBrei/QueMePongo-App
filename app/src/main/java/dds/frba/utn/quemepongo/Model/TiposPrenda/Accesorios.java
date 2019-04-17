package dds.frba.utn.quemepongo.Model.TiposPrenda;


import dds.frba.utn.quemepongo.Model.Prenda;
import dds.frba.utn.quemepongo.Model.TipoDeTela;

public class Accesorios extends Prenda {

    public Accesorios(Integer id, TipoDeTela tipoDeTela, String descripcion, String colorP, String colorS) {
        super(id, tipoDeTela, descripcion, colorP, colorS);
    }

    public Accesorios() {
    }
}
