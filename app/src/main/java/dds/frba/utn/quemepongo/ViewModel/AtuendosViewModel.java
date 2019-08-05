package dds.frba.utn.quemepongo.ViewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.support.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

import dds.frba.utn.quemepongo.Helpers.RetrofitInstanciator;
import dds.frba.utn.quemepongo.Model.Atuendo;
import dds.frba.utn.quemepongo.Model.WebServices.Request.Atuendo.GetAtuendosRequest;
import dds.frba.utn.quemepongo.QueMePongo;
import dds.frba.utn.quemepongo.Repository.AtuendosRespository;
import retrofit2.Call;
import retrofit2.Response;

public class AtuendosViewModel extends AndroidViewModel {
    private AtuendosRespository atuendosRespository;
    private QueMePongo application;
    private MutableLiveData<Atuendo> atuendo = new MutableLiveData<>();

    public AtuendosViewModel(@NonNull Application application) {
        super(application);
        this.application = (QueMePongo) application;

        atuendosRespository = RetrofitInstanciator
                .getInstance()
                .getRetrofit()
                .create(AtuendosRespository.class);
    }

    public void getAtuendo(LifecycleOwner owner, Observer<List<Atuendo>> observer) {
        application.getAtuendosActuales().observe(owner, observer);
    }

    public QueMePongo getApplication() {
        return application;
    }

    public AtuendosRespository getAtuendosRespository() {
        return this.atuendosRespository;
    }

    public void setAtuendos(List<Atuendo> atuendos){
        application.setAtuendos(atuendos);
    }
}
