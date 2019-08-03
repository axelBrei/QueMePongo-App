package dds.frba.utn.quemepongo.Utils;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;

import dds.frba.utn.quemepongo.View.QueMePongoActivity;

public class ActivityHelper {

    public static Intent startActivityWithBacbButtonBlocked(Context originContext, Class destination, @Nullable Bundle originalBundle) {
        Intent intent = new Intent(originContext, destination);
        Bundle bundle = originalBundle;
        if(bundle == null){
            bundle = new Bundle();
        }
        bundle.putBoolean(QueMePongoActivity.ENABLE_BACK_BUTTON, false);
        intent.putExtras(bundle);
        return intent;
    }

}
