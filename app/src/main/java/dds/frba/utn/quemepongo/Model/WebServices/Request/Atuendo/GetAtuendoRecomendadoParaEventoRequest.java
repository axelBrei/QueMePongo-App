package dds.frba.utn.quemepongo.Model.WebServices.Request.Atuendo;


import dds.frba.utn.quemepongo.Model.Evento;

public class GetAtuendoRecomendadoParaEventoRequest {
    private String username;
    private Integer idGuardarropa;
    private Evento evento;
    private Integer climaApi=0;

    public GetAtuendoRecomendadoParaEventoRequest() {
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

    public Evento getEvento() {
        return evento;
    }

    public void setEvento(Evento evento) {
        this.evento = evento;
    }

    public Integer getClimaApi() {
        return climaApi;
    }

    public void setEvento(Integer climaApi) {
        this.climaApi= climaApi;
    }

}
