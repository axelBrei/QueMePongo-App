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
import dds.frba.utn.quemepongo.Utils.OnCompleteListenner;
import dds.frba.utn.quemepongo.View.QueMePongoActivity;
import retrofit2.Call;
import retrofit2.Response;

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

    public static <T> CustomRetrofitCallback<T>  showCallbackErrorIfNeed (QueMePongoActivity activity, OnCompleteListenner<Response<T>> listenner){
        return new CustomRetrofitCallback<T>(){
            @Override
            public void onCustomResponse(Call<T> call, Response<T> response) {
                listenner.onComplete(response);
            }

            @Override
            public void onCustomFailure(Call<T> call, Error error) {
                activity.setProgressDialog(false);
                ShowSimpleError(activity, error.getMessage());
            }

            @Override
            public void onHttpRequestFail(Call<T> call, Throwable t) {
                activity.setProgressDialog(false);
                showGenericError(activity);
            }
        };
    }
}
