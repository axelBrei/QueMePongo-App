package dds.frba.utn.quemepongo.Model.WebServices.Response.Guardarropa.ResponseObjects;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GuardarropaResponseObject {
    private int id;
    private String descripcion;

    public GuardarropaResponseObject() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
