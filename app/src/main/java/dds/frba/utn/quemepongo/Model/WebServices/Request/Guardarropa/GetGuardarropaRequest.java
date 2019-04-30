package dds.frba.utn.quemepongo.Model.WebServices.Request.Guardarropa;

public class GetGuardarropaRequest {
    private String uid;

    public GetGuardarropaRequest() {
    }

    public GetGuardarropaRequest(String uid) {
        this.uid = uid;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
