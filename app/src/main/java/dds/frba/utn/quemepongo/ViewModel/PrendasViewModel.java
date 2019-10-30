package dds.frba.utn.quemepongo.ViewModel;


import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import dds.frba.utn.quemepongo.Helpers.RetrofitInstanciator;
import dds.frba.utn.quemepongo.Model.Prenda;
import dds.frba.utn.quemepongo.QueMePongo;
import dds.frba.utn.quemepongo.Repository.PrendasRepository;

public class PrendasViewModel extends AndroidViewModel {
    private MutableLiveData<List<Prenda>> prendas;
    private PrendasRepository prendasRepository;

    public PrendasViewModel(@NonNull Application application) {
        super(application);
        prendas = new MutableLiveData<>();
        prendasRepository = RetrofitInstanciator.instanciateRepository(PrendasRepository.class);
    }

    public void init(){

    }

    public void setPrendas(List<Prenda> pList) {
       prendas.setValue(pList);
    }

    public MutableLiveData<List<Prenda>> getPrendas() {
        return prendas;
    }

    public PrendasRepository getPrendasRepository() {
        return prendasRepository;
    }


    @NonNull
    public QueMePongo getApplication() {
        return (QueMePongo) super.getApplication();
    }
}
