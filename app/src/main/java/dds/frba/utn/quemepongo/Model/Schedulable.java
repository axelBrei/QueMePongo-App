package dds.frba.utn.quemepongo.Model;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;

public interface Schedulable {
    void startLoading();
    void stopLoading();
    Context getContext();
}
