package dds.frba.utn.quemepongo;

import android.app.Application;
import android.arch.lifecycle.MutableLiveData;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

import dds.frba.utn.quemepongo.Helpers.CustomRetrofitCallback;
import dds.frba.utn.quemepongo.Helpers.RetrofitInstanciator;
import dds.frba.utn.quemepongo.Model.Guardarropa;
import dds.frba.utn.quemepongo.Model.Prenda;
import dds.frba.utn.quemepongo.Model.WebServices.Request.Prendas.GetPrendasRequest;
import dds.frba.utn.quemepongo.Model.WebServices.Response.Guardarropa.GetGuardarropasResponse;
import dds.frba.utn.quemepongo.Model.WebServices.Response.Guardarropa.ResponseObjects.GuardarropaResponseObject;
import dds.frba.utn.quemepongo.Repository.PrendasRepository;
import dds.frba.utn.quemepongo.Utils.JsonParser.PrendasContainer;
import retrofit2.Call;
import retrofit2.Response;

public class QueMePongo extends Application {
    private List<Guardarropa> guardarropas;
    private MutableLiveData<Guardarropa> guardarropaActual = new MutableLiveData<>();
    public MutableLiveData<Boolean> loading = new MutableLiveData<>();
    private PrendasRepository prendasRepository;

    @Override
    public void onCreate() {
        super.onCreate();
        loading.setValue(false);
        guardarropaActual.setValue(new Guardarropa());
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
                .enqueue(new CustomRetrofitCallback<PrendasContainer>() {
                    @Override
                    public void onCustomResponse(Call<PrendasContainer> call, Response<PrendasContainer> response) {
                        guardarropa.setPrendas(response.body().getPrendaslist());
                        guardarropaActual.setValue(guardarropa);
                        loading.setValue(false);
                    }

                    @Override
                    public void onCustomFailure(Call<PrendasContainer> call, Error error) {
                        loading.setValue(false);
                    }

                    @Override
                    public void onHttpRequestFail(Call<PrendasContainer> call, Throwable t) {
                        loading.setValue(false);
                    }
                });
    }

    public List<Guardarropa> getGuardarropas() {
        return guardarropas;
    }

    public void setGuardarropas(List<Guardarropa> guardarropas) {
        this.guardarropas = guardarropas;
    }

    public void setGuardarropas(GetGuardarropasResponse guardarropasResponse){
        List<Guardarropa> guardarropasAux = new ArrayList<>();
        if(guardarropasResponse == null || guardarropasResponse.getGuardarropas().size() == 0) return;
        for (GuardarropaResponseObject g :guardarropasResponse.getGuardarropas()) {
            guardarropasAux.add( new Guardarropa(g.getId(), g.getDesc()));
        }
        this.setGuardarropas(guardarropasAux);
    }

    public void addPrendaToGuardarropa(Prenda prenda){
        Guardarropa g = guardarropaActual.getValue();
        g.aniadirPrenda(prenda);
        guardarropaActual.setValue(g);
    }
}
