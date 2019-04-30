package dds.frba.utn.quemepongo.Model.WebServices.Request.Prendas;

public class GetPrendasRequest {
    private String uid;
    private String idGuardarropa;

    public GetPrendasRequest() {
    }

    public GetPrendasRequest(String uid, String idGuardarropa) {
        this.uid = uid;
        this.idGuardarropa = idGuardarropa;
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
}
