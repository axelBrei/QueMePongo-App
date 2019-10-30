package dds.frba.utn.quemepongo.Model;

import android.app.Activity;

public interface Schedulable {
    void startLoading();
    void stopLoading();
    Activity getContext();
}
