package dds.frba.utn.quemepongo;

import android.app.Application;
import android.arch.lifecycle.MutableLiveData;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dds.frba.utn.quemepongo.Helpers.CustomRetrofitCallback;
import dds.frba.utn.quemepongo.Helpers.ErrorHelper;
import dds.frba.utn.quemepongo.Helpers.RetrofitInstanciator;
import dds.frba.utn.quemepongo.Model.Atuendo;
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
    private MutableLiveData<List<Atuendo>> atuendosActuales = new MutableLiveData<>();
    private MutableLiveData<Map<String, List<Atuendo>>> atuendosMap = new MutableLiveData<>();
    public MutableLiveData<Boolean> loading = new MutableLiveData<>();
    private PrendasRepository prendasRepository;

    @Override
    public void onCreate() {
        super.onCreate();
        RetrofitInstanciator.initializeRetrofit(this);

        loading.setValue(false);
        guardarropaActual.setValue(new Guardarropa());
        atuendosActuales.setValue(new ArrayList<>());
        atuendosMap.setValue(new HashMap<>());
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
                        atuendosActuales.setValue(atuendosMap.getValue().get(String.valueOf(guardarropa.getId())));
                        loading.setValue(false);
                    }

                    @Override
                    public void onCustomFailure(Call<PrendasContainer> call, Error error) {
                        loading.setValue(false);
                        Toast.makeText(QueMePongo.this, "Ha ocurrido un error al buscar el guardarropa desdeado", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onHttpRequestFail(Call<PrendasContainer> call, Throwable t) {
                        loading.setValue(false);
                        Toast.makeText(QueMePongo.this, "Ha ocurrido un error al buscar el guardarropa desdeado", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public List<Guardarropa> getGuardarropas() {
        return guardarropas;
    }

    public MutableLiveData<List<Atuendo>> getAtuendosActuales() {
        return atuendosActuales;
    }

    public void setGuardarropas(List<Guardarropa> guardarropas) {
        this.guardarropas = guardarropas;
    }

    public void setGuardarropas(GetGuardarropasResponse guardarropasResponse){
        List<Guardarropa> guardarropasAux = new ArrayList<>();
        Map<String, List<Atuendo>> atuendosMapAux = new HashMap<>();
        if(guardarropasResponse == null || guardarropasResponse.getGuardarropas().size() == 0) return;
        for (GuardarropaResponseObject g :guardarropasResponse.getGuardarropas()) {
            guardarropasAux.add( new Guardarropa(g.getId(), g.getDesc()));
            atuendosMapAux.put(String.valueOf(g.getId()), new ArrayList<>());
        }
        this.setGuardarropas(guardarropasAux);
        atuendosMap.setValue(atuendosMapAux);
    }

    public void addPrendaToGuardarropa(Prenda prenda){
        Guardarropa g = guardarropaActual.getValue();
        g.aniadirPrenda(prenda);
        guardarropaActual.setValue(g);
    }

    public void addAtuendo(Atuendo atuendo){
        List<Atuendo> atuendosAux = atuendosActuales.getValue();
        atuendosAux.add(atuendo);
        atuendosActuales.postValue(atuendosAux);
    }

}
