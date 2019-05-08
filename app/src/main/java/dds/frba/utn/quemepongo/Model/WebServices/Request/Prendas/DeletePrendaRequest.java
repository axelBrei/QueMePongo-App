package dds.frba.utn.quemepongo.Model.WebServices.Request.Prendas;

import dds.frba.utn.quemepongo.Model.Prenda;
import dds.frba.utn.quemepongo.Model.WebServices.PrendaRequestObject;

public class DeletePrendaRequest {
    private String uid;
    private String idGuardarropa;
    private PrendaRequestObject prenda;

    public DeletePrendaRequest() {
    }

    public DeletePrendaRequest(String uid, String idGuardarropa, PrendaRequestObject prenda) {
        this.uid = uid;
        this.idGuardarropa = idGuardarropa;
        this.prenda = prenda;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getIdGuardarropa() {
        return idGuardarropa;
    }

    public void setIdGuardarropa(String idGuardarropa) {
        this.idGuardarropa = idGuardarropa;
    }

    public PrendaRequestObject getPrenda() {
        return prenda;
    }

    public void setPrenda(PrendaRequestObject prenda) {
        this.prenda = prenda;
    }
}
