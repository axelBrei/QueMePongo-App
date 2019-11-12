package dds.frba.utn.quemepongo.Helpers;


import android.content.Context;
import android.widget.ImageView;
import android.widget.Toast;

import com.github.javiersantos.materialstyleddialogs.MaterialStyledDialog;

import dds.frba.utn.quemepongo.Model.Schedulable;
import dds.frba.utn.quemepongo.Model.WebServices.Error;
import dds.frba.utn.quemepongo.QueMePongo;
import dds.frba.utn.quemepongo.R;
import dds.frba.utn.quemepongo.Utils.CustomListenners.OnCompleteListenerWithStatus;
import dds.frba.utn.quemepongo.Utils.CustomListenners.OnCompleteListenner;
import dds.frba.utn.quemepongo.View.Fragments.ErrorFragment;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ErrorHelper {

    private static MaterialStyledDialog.Builder getDefaultErrorBuilder(Context activity){
       return new MaterialStyledDialog.Builder(activity)
               .setCancelable(true)
               .setHeaderColor(R.color.red_error)
               .setHeaderScaleType(ImageView.ScaleType.FIT_CENTER)
               .setHeaderDrawable(R.drawable.ic_error_white);

    }

    public static void showSimpleError(Context activity, String errorMessage){
        Toast.makeText(activity, errorMessage, Toast.LENGTH_SHORT).show();
//         getDefaultErrorBuilder(activity)
//            .setDescription(errorMessage)
//             .setNeutralText("Aceptar")
//            .build()
//            .show();

    }

    public static void showGenericError(Context activity){
        getDefaultErrorBuilder(activity)
            .setDescription("Ha ocurrido un error al comunicarse con el servidor")
            .setNeutralText("Aceptar")
            .build()
            .show();

    }

    public static void showRetryError(Context context, Call call, Callback callback){
        getDefaultErrorBuilder(context)
                .setPositiveText("Intentar de nuevo")
                .onPositive((dialog, which) -> {
                    call.clone().enqueue(callback);
                    dialog.dismiss();
                })
                .setNegativeText("Cancelar")
                .setDescription("Ha ocurrido un error al comunicarse con el servidor")
                .build()
                .show();
    }

    public <T> CustomRetrofitCallback<T>  showCallbackErrorIfNeed(Schedulable schedulable, OnCompleteListenner<T> listenner){
        schedulable.startLoading();
        return new CustomRetrofitCallback<T>(){
            @Override
            public void onCustomResponse(Call<T> call, Response<T> response) {
                schedulable.stopLoading();
                listenner.onComplete(response.body());
            }

            @Override
            public void onCustomFailure(Call<T> call, Error error) {
                schedulable.stopLoading();
                showSimpleError(schedulable.getContext(), error.getMessage());
            }

            @Override
            public void onHttpRequestFail(Call<T> call, Throwable t) {
                schedulable.stopLoading();
                ErrorFragment fragment = new ErrorFragment();
                fragment.setCall(call);
                fragment.setCallback(this);
                schedulable.getContext().getFragmentManager().beginTransaction().add(android.R.id.content, fragment,"").commit();
            }
        };
    }

    public <T> CustomRetrofitCallback<T>  showCallbackErrorIfNeed(Schedulable schedulable, OnCompleteListenner<T> succedListenner, OnCompleteListenner<Error> errorListener){
        schedulable.startLoading();
        return new CustomRetrofitCallback<T>(){
            @Override
            public void onCustomResponse(Call<T> call, Response<T> response) {
                schedulable.stopLoading();
                succedListenner.onComplete(response.body());
            }

            @Override
            public void onCustomFailure(Call<T> call, Error error) {
                if(errorListener != null)
                    errorListener.onComplete(error);
                schedulable.stopLoading();
                showSimpleError(schedulable.getContext(), error.getMessage());
            }

            @Override
            public void onHttpRequestFail(Call<T> call, Throwable t) {
                schedulable.stopLoading();
                ErrorFragment fragment = new ErrorFragment();
                fragment.setCall(call);
                fragment.setCallback(this);
                schedulable.getContext().getFragmentManager().beginTransaction().add(android.R.id.content, fragment,"").commit();

            }
        };
    }

    public <T> CustomRetrofitCallback<T> showToastErrorInCaseIsNeeded(QueMePongo application, OnCompleteListenerWithStatus listenerWithStatus){
        application.startLoading();
        return new CustomRetrofitCallback<T>() {
            @Override
            public void onCustomResponse(Call<T> call, Response<T> response) {
                application.stopLoading();
                listenerWithStatus.onComplete(true, response.body());
            }

            @Override
            public void onCustomFailure(Call<T> call, Error error) {
                application.stopLoading();
                showSimpleError(application.getContext(), error.getMessage());
                listenerWithStatus.onComplete(false, error);
            }

            @Override
            public void onHttpRequestFail(Call<T> call, Throwable t) {
                application.stopLoading();
                showSimpleError(application, "Ha ocurrido un error");
            }
        };
    }
}
