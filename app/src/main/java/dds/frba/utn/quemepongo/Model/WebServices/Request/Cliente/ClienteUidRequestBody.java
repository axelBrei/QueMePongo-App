package dds.frba.utn.quemepongo.Model.WebServices.Request.Cliente;

public class ClienteUidRequestBody {
    private String uid;

    public ClienteUidRequestBody(String uid) {
        this.uid = uid;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

}