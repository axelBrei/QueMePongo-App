package dds.frba.utn.quemepongo.Model.WebServices.Response.Prendas;

public class AddPrendaResponse {
    private String message;
    private int idPrenda;

    public AddPrendaResponse() {
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getIdPrenda() {
        return idPrenda;
    }

    public void setIdPrenda(int idPrenda) {
        this.idPrenda = idPrenda;
    }
}
