package dds.frba.utn.quemepongo.Model.WebServices.Request.Evento;

import dds.frba.utn.quemepongo.Model.Evento;

public class SendEventoRequest {

    private String username;
    private String idGuardarropa;
    private Evento evento;

    public SendEventoRequest() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getIdGuardarropa() {
        return idGuardarropa;
    }

    public void setIdGuardarropa(String idGuardarropa) {
        this.idGuardarropa = idGuardarropa;
    }

    public Evento getEvento() {
        return evento;
    }

    public void setEvento(Evento evento) {
        this.evento = evento;
    }
}
