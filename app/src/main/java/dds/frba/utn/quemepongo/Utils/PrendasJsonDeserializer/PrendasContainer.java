package dds.frba.utn.quemepongo.Utils.PrendasJsonDeserializer;

import java.util.ArrayList;
import java.util.List;

import dds.frba.utn.quemepongo.Model.Prenda;

public class PrendasContainer {
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
