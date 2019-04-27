package dds.frba.utn.quemepongo.Model.WebServices;

import dds.frba.utn.quemepongo.Model.Prenda;

public class NuevaPrendaReq extends Prenda {
    private String parteQueOcupa;

    public NuevaPrendaReq(String parteQueOcupa, Prenda prenda) {
        this.parteQueOcupa = parteQueOcupa.split("dds.frba.utn.quemepongo.Model.TiposPrenda.")[1];;
        super.setColorP(prenda.getColorP());
        super.setColorS(prenda.getColorP());
        super.setDescripcion(prenda.getDescripcion());
        super.setId(prenda.getId());
        super.setTipoDePrenda(prenda.getTipoDePrenda());
        super.setTipoDeTela(prenda.getTipoDeTela());
    }

    public void setParteQueOcupa(String parteQueOcupa) {
        this.parteQueOcupa = parteQueOcupa;
    }

    public String getParteQueOcupa() {
        return parteQueOcupa;
    }
}
