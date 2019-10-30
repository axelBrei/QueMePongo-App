package dds.frba.utn.quemepongo;

import android.app.Activity;
import android.app.Application;

import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;

import dds.frba.utn.quemepongo.Controllers.GuardarropaController;
import dds.frba.utn.quemepongo.Helpers.ErrorHelper;
import dds.frba.utn.quemepongo.Helpers.RetrofitInstanciator;
import dds.frba.utn.quemepongo.Model.Guardarropa;
import dds.frba.utn.quemepongo.Model.Prenda;
import dds.frba.utn.quemepongo.Model.Schedulable;
import dds.frba.utn.quemepongo.Model.WebServices.Response.Guardarropa.GetGuardarropasResponse;
import dds.frba.utn.quemepongo.Model.WebServices.Response.Guardarropa.ResponseObjects.GuardarropaResponseObject;
import dds.frba.utn.quemepongo.Repository.GuardarropasRepository;
import dds.frba.utn.quemepongo.Utils.ActivityLifeCycleCallbackImpl;
import dds.frba.utn.quemepongo.Utils.CustomListenners.OnCompleteListenerWithStatusAndApplication;
import lombok.Getter;
import lombok.Setter;

@Getter
public class QueMePongo extends Application implements Schedulable {

    private MutableLiveData<List<Guardarropa>> guardarropas = new MutableLiveData<>();

    private MutableLiveData<Guardarropa> guardarropaActual = new MutableLiveData<>();
    public MutableLiveData<Boolean> loading = new MutableLiveData<>();

    private ActivityLifeCycleCallbackImpl activityLifeCycleCallback;
    private Boolean shouldUpdateGuardarropa = false;

    private GuardarropaController guardarropaController;

    @Override
    public void onCreate() {
        super.onCreate();
        activityLifeCycleCallback = new ActivityLifeCycleCallbackImpl();
        registerActivityLifecycleCallbacks(activityLifeCycleCallback);
        RetrofitInstanciator.initializeRetrofit(this);

        loading.setValue(false);

        guardarropaController = new GuardarropaController(QueMePongo.this);

        guardarropaActual.observeForever( guardarropa ->  {
            if(!QueMePongo.this.shouldUpdateGuardarropa) {
                return;
            }
            guardarropaController.getGuardarropaCompleto(
                    guardarropa.getId(),
                    new OnCompleteListenerWithStatusAndApplication() {
                        @Override
                        public void onComplete(Boolean success, QueMePongo application, Object obj) {
                            if(success){
                                shouldUpdateGuardarropa = false;
                                guardarropaActual.setValue( (Guardarropa) obj);
                            }
                        }
                    }
            );
        });
    }

    public MutableLiveData<Guardarropa> getGuardarropaActual() {
        return guardarropaActual;
    }

    public void setGuardarropaActual(Guardarropa guardarropa) {
        shouldUpdateGuardarropa = true;
        this.guardarropaActual.setValue(guardarropa);
    }

    public void setGuardarropas(GetGuardarropasResponse guardarropasResponse){
        if(guardarropasResponse == null || guardarropasResponse.getGuardarropas().size() == 0) return;
        List<Guardarropa> guardarropasAux = new ArrayList<>();
        for (GuardarropaResponseObject g :guardarropasResponse.getGuardarropas()) {
            guardarropasAux.add(new Guardarropa(g.getId(), g.getDescripcion()));
        }
        guardarropas.setValue(guardarropasAux);
        shouldUpdateGuardarropa = true;
        guardarropaActual.setValue(guardarropas.getValue().get(0));
    }


    public void addPrendaToGuardarropa(Prenda prenda){
        Guardarropa g = guardarropaActual.getValue();
        g.getPrendas().add(prenda);
        guardarropaActual.setValue(g);
    }

    public void deleteGuardarropa(Guardarropa g){
        List<Guardarropa> aux = guardarropas.getValue();
        aux.remove(g);
        guardarropas.setValue(aux);
    }

    public void addGuardarropa(Guardarropa g) {
        List<Guardarropa> aux = guardarropas.getValue();
        aux.add(g);
        guardarropas.setValue(aux);
    }

    @Override
    public void startLoading() {
        loading.setValue(true);
    }

    @Override
    public void stopLoading() {
        loading.setValue(false);
    }

    @Override
    public Activity getContext() {
        return activityLifeCycleCallback.getCurrentActivity();
    }
}
