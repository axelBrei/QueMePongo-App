package dds.frba.utn.quemepongo.Model;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Guardarropa {
    int id;
    String descripcion;
    String uidDueno;
    List<Prenda> prendas;
    List<Atuendo> atuendos;

    public Guardarropa(int id, String descripcion) {
        this.id = id;
        this.descripcion = descripcion;
    }

    @NonNull
    @Override
    public String toString() {
        return this.getDescripcion();
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        Guardarropa g = (Guardarropa) obj;
        return g.getId() == this.id;
    }
}
