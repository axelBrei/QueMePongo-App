package dds.frba.utn.quemepongo.Model.WebServices.Response.Guardarropa;

import java.util.List;

import dds.frba.utn.quemepongo.Model.WebServices.Response.Guardarropa.ResponseObjects.GuardarropaResponseObject;

public class GetGuardarropasResponse {
    private List<GuardarropaResponseObject> guardarropas;

    public GetGuardarropasResponse(List<GuardarropaResponseObject> guardarropas) {
        this.guardarropas = guardarropas;
    }

    public GetGuardarropasResponse() {
    }

    public List<GuardarropaResponseObject> getGuardarropas() {
        return guardarropas;
    }

    public void setGuardarropas(List<GuardarropaResponseObject> guardarropas) {
        this.guardarropas = guardarropas;
    }
}
