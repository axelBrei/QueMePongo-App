package dds.frba.utn.quemepongo.Utils.JsonParser;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

import dds.frba.utn.quemepongo.Model.Prenda;

public class PrendasContainer {
    @JsonProperty("prendas")
    private List<Prenda> prendaslist = new ArrayList<>();

    public PrendasContainer(List<Prenda> prendaslist) {
        this.prendaslist = prendaslist;
    }

    public PrendasContainer() {
    }

    public List<Prenda> getPrendaslist() {
        return prendaslist;
    }

    public void setPrendaslist(List<Prenda> prendaslist) {
        this.prendaslist = prendaslist;
    }
}
