package dds.frba.utn.quemepongo.Utils;

public interface Validable {
    Object getContent();
    void setError(String error);
    void cleanErrorOnChange();
}
