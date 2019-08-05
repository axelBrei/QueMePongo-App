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
import dds.frba.utn.quemepongo.Repository.PrendasRepository;
import dds.frba.utn.quemepongo.Utils.ActivityLifeCycleCallbackImpl;
import dds.frba.utn.quemepongo.Utils.JsonParser.PrendasContainer;
import dds.frba.utn.quemepongo.Utils.OnCompleteListenner;


public class QueMePongo extends Application implements Schedulable {

    private MutableLiveData<List<Guardarropa>> guardarropas = new MutableLiveData<>();
    private MutableLiveData<Guardarropa> guardarropaActual = new MutableLiveData<>();
    private MutableLiveData<List<Atuendo>> atuendosActuales = new MutableLiveData<>();
    private MutableLiveData<Map<String, List<Atuendo>>> atuendosMap = new MutableLiveData<>();
    public MutableLiveData<Boolean> loading = new MutableLiveData<>();
    private PrendasRepository prendasRepository;
    private ActivityLifeCycleCallbackImpl activityLifeCycleCallback;

    @Override
    public void onCreate() {
        super.onCreate();
        activityLifeCycleCallback = new ActivityLifeCycleCallbackImpl();
        registerActivityLifecycleCallbacks(activityLifeCycleCallback);
        RetrofitInstanciator.initializeRetrofit(this);

        loading.setValue(false);
        guardarropaActual.setValue(new Guardarropa());
        atuendosActuales.setValue(new ArrayList<>());
        atuendosMap.setValue(new HashMap<>());
        guardarropas.setValue(new ArrayList<>());
        prendasRepository = RetrofitInstanciator
                .getInstance()
                .getRetrofit()
                .create(PrendasRepository.class);

    }

    public MutableLiveData<Guardarropa> getGuardarropaActual() {
        return guardarropaActual;
    }

    public void setGuardarropaActual(Guardarropa guardarropa) {

        loading.setValue(true);
        prendasRepository
                .getPrendas(new GetPrendasRequest(FirebaseAuth.getInstance().getCurrentUser().getUid(), String.valueOf(guardarropa.getId())))
                .enqueue(new ErrorHelper().showCallbackErrorIfNeed(this,
                        new OnCompleteListenner<PrendasContainer>() {
                            @Override
                            public void onComplete(PrendasContainer param) {
                                guardarropa.setPrendas(param.getPrendaslist());
                                guardarropaActual.setValue(guardarropa);
                                atuendosActuales.setValue(atuendosMap.getValue().get(String.valueOf(guardarropa.getId())));
                                loading.setValue(false);
                            }
                        }
                ));

    }

    public List<Guardarropa> getGuardarropas() {
        return guardarropas.getValue();
    }

    public MutableLiveData<List<Guardarropa>> getGuardarropaObserver(){
        return guardarropas;
    }

    public MutableLiveData<List<Atuendo>> getAtuendosActuales() {
        return atuendosActuales;
    }

    public void setGuardarropas(List<Guardarropa> guardarropas) {
        this.guardarropas.setValue(guardarropas);
    }

    public void setGuardarropas(GetGuardarropasResponse guardarropasResponse){
        List<Guardarropa> guardarropasAux = new ArrayList<>();
        Map<String, List<Atuendo>> atuendosMapAux = new HashMap<>();
        if(guardarropasResponse == null || guardarropasResponse.getGuardarropas().size() == 0) return;
        for (GuardarropaResponseObject g :guardarropasResponse.getGuardarropas()) {
            guardarropasAux.add( new Guardarropa(g.getId(), g.getDescripcion()));
            atuendosMapAux.put(String.valueOf(g.getId()), new ArrayList<>());
        }
        this.setGuardarropas(guardarropasAux);
        atuendosMap.setValue(atuendosMapAux);
    }

    public void addGuardarropa(Guardarropa g){
        List<Guardarropa> guardarropasAux = guardarropas.getValue();
        Map<String, List<Atuendo>> atuendosAux = atuendosMap.getValue();
        guardarropasAux.add(g);
        guardarropas.postValue(guardarropasAux);
        atuendosAux.put(String.valueOf(g.getId()), new ArrayList<>());
        atuendosMap.postValue(atuendosAux);
    }

    public void addPrendaToGuardarropa(Prenda prenda){
        Guardarropa g = guardarropaActual.getValue();
        g.aniadirPrenda(prenda);
        guardarropaActual.setValue(g);
    }


    public void setAtuendos(List<Atuendo> atuendos){
        atuendosActuales.postValue(atuendos);
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
