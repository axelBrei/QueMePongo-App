package dds.frba.utn.quemepongo.Model.WebServices.Request.Atuendo;

public class GetAtuendosRequest {
    private String username;
    private Integer idGuardarropa;

    public GetAtuendosRequest() {
    }

    public GetAtuendosRequest(String username, Integer idGuardarropa) {
        this.username = username;
        this.idGuardarropa = idGuardarropa;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getIdGuardarropa() {
        return idGuardarropa;
    }

    public void setIdGuardarropa(Integer idGuardarropa) {
        this.idGuardarropa = idGuardarropa;
    }
}
