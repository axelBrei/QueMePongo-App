package dds.frba.utn.quemepongo.Model;

import java.util.ArrayList;
import java.util.List;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Cliente {

    long id;
    String uid;
    String mail;
    String nombre;
    String firebaseToken;
    List<Guardarropa> guardarropas;
    List<Evento> eventos;

    public Cliente(String uid, String mail, String username) {
        this.uid = uid;
        this.mail = mail;
        this.nombre = username;
    }
}
