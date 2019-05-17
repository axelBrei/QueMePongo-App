package dds.frba.utn.quemepongo.Helpers;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Looper;
import android.support.design.chip.Chip;
import android.support.design.widget.Snackbar;
import android.widget.ImageView;
import android.widget.Toast;

import com.github.javiersantos.materialstyleddialogs.MaterialStyledDialog;

import dds.frba.utn.quemepongo.QueMePongo;
import dds.frba.utn.quemepongo.R;

public class ErrorHelper {

    private static MaterialStyledDialog.Builder getDefaultErrorBuilder(Activity activity){
       return new MaterialStyledDialog.Builder(activity)
               .setCancelable(true)
               .setNeutralText("Aceptar")
               .setHeaderColor(R.color.red_error)
               .setHeaderScaleType(ImageView.ScaleType.FIT_CENTER)
               .setHeaderDrawable(R.drawable.ic_error_white);

    }

    public static void ShowSimpleError(Activity activity, String errorMessage){
        activity.runOnUiThread(
                () -> getDefaultErrorBuilder(activity)
                        .setDescription(errorMessage)
                        .build()
                        .show()

        );

    }

    public static void showGenericError(Activity activity){
        activity.runOnUiThread(
                () -> getDefaultErrorBuilder(activity)
                        .setDescription("Ha ocurrido un error al comunicarse con el servidor")
                        .build()
                        .show()
        );
    }
}
