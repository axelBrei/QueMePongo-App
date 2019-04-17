package dds.frba.utn.quemepongo.Model.TiposPrenda;

import dds.frba.utn.quemepongo.Model.Prenda;
import dds.frba.utn.quemepongo.Model.TipoDeTela;

public class PrendaSuperior extends Prenda {

    public PrendaSuperior(Integer id, TipoDeTela tipoDeTela, String descripcion, String colorP, String colorS) {
        super(id, tipoDeTela, descripcion, colorP, colorS);
    }

    public PrendaSuperior() {
    }
}
