package dds.frba.utn.quemepongo;

import android.app.Activity;
import android.app.Application;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dds.frba.utn.quemepongo.Helpers.ErrorHelper;
import dds.frba.utn.quemepongo.Helpers.RetrofitInstanciator;
import dds.frba.utn.quemepongo.Model.Atuendo;
import dds.frba.utn.quemepongo.Model.Guardarropa;
import dds.frba.utn.quemepongo.Model.Prenda;
import dds.frba.utn.quemepongo.Model.Schedulable;
import dds.frba.utn.quemepongo.Model.WebServices.Request.Prendas.GetPrendasRequest;
import dds.frba.utn.quemepongo.Model.WebServices.Response.Guardarropa.GetGuardarropasResponse;
import dds.frba.utn.quemepongo.Model.WebServices.Response.Guardarropa.ResponseObjects.GuardarropaResponseObject;
import dds.frba.utn.quemepongo.Repository.GuardarropasRepository;
import dds.frba.utn.quemepongo.Repository.PrendasRepository;
import dds.frba.utn.quemepongo.Utils.ActivityLifeCycleCallbackImpl;
import dds.frba.utn.quemepongo.Utils.JsonParser.PrendasContainer;
import dds.frba.utn.quemepongo.Utils.OnCompleteListenner;
import lombok.Getter;
import lombok.Setter;

@Getter
public class QueMePongo extends Application implements Schedulable {

    private String firebaseId;

    private List<Guardarropa> guardarropas = new ArrayList<>();

    private MutableLiveData<Guardarropa> guardarropaActual = new MutableLiveData<>();
    public MutableLiveData<Boolean> loading = new MutableLiveData<>();
    private GuardarropasRepository guardarropasRepository;
    private ActivityLifeCycleCallbackImpl activityLifeCycleCallback;
    private Boolean shouldUpdateGuardarropa = false;

    @Override
    public void onCreate() {
        super.onCreate();
        activityLifeCycleCallback = new ActivityLifeCycleCallbackImpl();
        registerActivityLifecycleCallbacks(activityLifeCycleCallback);
        RetrofitInstanciator.initializeRetrofit(this);

        loading.setValue(false);
        firebaseId = FirebaseAuth.getInstance().getUid();
        guardarropasRepository = RetrofitInstanciator.instanciateRepository(GuardarropasRepository.class);


        guardarropaActual.observeForever( guardarropa ->  {
            guardarropa.getPrendas();
            // TODO: cambiar esto, no cambian los guardarropas
            System.out.println(shouldUpdateGuardarropa);
            if(!QueMePongo.this.shouldUpdateGuardarropa) {
                return;
            }
            startLoading();
            guardarropasRepository.getGuardarropas(firebaseId, guardarropa.getId())
                    .enqueue(
                            new ErrorHelper().showCallbackErrorIfNeed(this,
                                    g -> {
                                        shouldUpdateGuardarropa = false;
                                        guardarropaActual.setValue(g);
                                        stopLoading();
                                    }
                            )
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
        for (GuardarropaResponseObject g :guardarropasResponse.getGuardarropas()) {
            this.guardarropas.add(new Guardarropa(g.getId(), g.getDescripcion()));
        }
        shouldUpdateGuardarropa = true;
        guardarropaActual.setValue(guardarropas.get(0));
    }


    public void addPrendaToGuardarropa(Prenda prenda){
        Guardarropa g = guardarropaActual.getValue();
        g.getPrendas().add(prenda);
        guardarropaActual.setValue(g);
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
