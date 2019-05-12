package dds.frba.utn.quemepongo.Model.WebServices.Request.Atuendo;

public class GetAtuendoRequest {
    private String username;
    private Integer idGuardarropa;

    public GetAtuendoRequest(String username, Integer idGuardarropa) {
        this.username = username;
        this.idGuardarropa = idGuardarropa;
    }

    public String getUsername() {
        return username;
    }

    public Integer getIdGuardarropa() {
        return idGuardarropa;
    }
}
