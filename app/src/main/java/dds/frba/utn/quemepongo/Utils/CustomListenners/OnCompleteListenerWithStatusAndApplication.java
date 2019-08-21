package dds.frba.utn.quemepongo.Utils.CustomListenners;

import androidx.annotation.Nullable;

import dds.frba.utn.quemepongo.QueMePongo;

public interface OnCompleteListenerWithStatusAndApplication {
    void onComplete(Boolean success, QueMePongo application,@Nullable Object obj);
}
