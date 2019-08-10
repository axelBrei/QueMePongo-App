package dds.frba.utn.quemepongo.ViewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.support.annotation.NonNull;

import java.util.List;

import dds.frba.utn.quemepongo.Helpers.RetrofitInstanciator;
import dds.frba.utn.quemepongo.Model.Atuendo;
import dds.frba.utn.quemepongo.Model.Guardarropa;
import dds.frba.utn.quemepongo.QueMePongo;
import dds.frba.utn.quemepongo.Repository.AtuendosRespository;

public class AtuendosViewModel extends AndroidViewModel {
    private AtuendosRespository atuendosRespository;
    private QueMePongo application;
    private MutableLiveData<Atuendo> atuendo = new MutableLiveData<>();

    public AtuendosViewModel(@NonNull Application application) {
        super(application);
        this.application = (QueMePongo) application;

        atuendosRespository = RetrofitInstanciator.instanciateRepository(AtuendosRespository.class);
    }

    public void getAtuendo(LifecycleOwner owner, Observer<Guardarropa> observer) {
        application.getGuardarropaActual().observe(owner, observer);
    }

    public QueMePongo getApplication() {
        return application;
    }

    public AtuendosRespository getAtuendosRespository() {
        return this.atuendosRespository;
    }

    public void setAtuendos(List<Atuendo> atuendos){
//        application.setAtuendos(atuendos);
    }
}
