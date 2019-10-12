package dds.frba.utn.quemepongo.Utils;

import dds.frba.utn.quemepongo.Model.Guardarropa;

public interface Swipeable {
    void onSwipeLeft(Guardarropa g);
    void onSwipeRight(Guardarropa g);
    void onPress(Guardarropa g);
}
