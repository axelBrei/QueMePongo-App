package dds.frba.utn.quemepongo.Model.TiposPrenda;

import dds.frba.utn.quemepongo.Model.Prenda;
import dds.frba.utn.quemepongo.Model.TipoDeTela;

public class Superior extends Prenda {
    private Integer idTipo;
    private Integer nivelAbrigo;

    public Superior() {
    }

    public Integer getIdTipo() {
        return idTipo;
    }

    public void setIdTipo(Integer idTipo) {
        this.idTipo = idTipo;
    }

    public Integer getNivelAbrigo() {
        return nivelAbrigo;
    }

    public void setNivelAbrigo(Integer nivelAbrigo) {
        this.nivelAbrigo = nivelAbrigo;
    }
}
