package dds.frba.utn.quemepongo.Utils.CustomListenners;

public interface OnCompleteListenerWithStatus <T extends Object> {
    void onComplete(Boolean succed,T obj);
}
