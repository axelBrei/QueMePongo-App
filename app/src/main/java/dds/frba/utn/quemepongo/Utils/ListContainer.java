package dds.frba.utn.quemepongo.Utils;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class ListContainer<T>{
    @JsonProperty("list")
    private List<T> list;

    public ListContainer(List list) {
        this.list = list;
    }

    @JsonCreator(mode = JsonCreator.Mode.DEFAULT)
    public ListContainer() {
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }
}
