package dds.frba.utn.quemepongo.View.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import dds.frba.utn.quemepongo.Helpers.CustomRetrofitCallback;
import dds.frba.utn.quemepongo.Helpers.RetrofitInstanciator;
import dds.frba.utn.quemepongo.Model.Atuendo;
import dds.frba.utn.quemepongo.Model.WebServices.Request.Atuendo.GetAtuendoRequest;
import dds.frba.utn.quemepongo.QueMePongo;
import dds.frba.utn.quemepongo.R;
import dds.frba.utn.quemepongo.Repository.AtuendosRepository;
import retrofit2.Call;
import retrofit2.Response;

public class AtuendosFragment extends Fragment {
    private AtuendosRepository atuendosRepository;
    private QueMePongo application;

    public AtuendosFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_atuendos, container, false);
        application = ( (QueMePongo) getActivity().getApplication() );
        atuendosRepository = RetrofitInstanciator
                .getInstance()
                .getRetrofit()
                .create(AtuendosRepository.class);

        fetchAtuendos();

        return view;
    }

    private void fetchAtuendos(){
        atuendosRepository.getAtuendo(
                new GetAtuendoRequest(
                        FirebaseAuth.getInstance().getCurrentUser().getUid(),
                        application.getGuardarropaActual().getValue().getId()
                )
        ).enqueue(getAtuendoCallback());
    }

    private CustomRetrofitCallback<Atuendo> getAtuendoCallback(){
        return new CustomRetrofitCallback<Atuendo>() {
            @Override
            public void onCustomResponse(Call<Atuendo> call, Response<Atuendo> response) {
                Toast.makeText(getActivity(), "Hola", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCustomFailure(Call<Atuendo> call, Error error) {
                Toast.makeText(getActivity(), "Hola", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onHttpRequestFail(Call<Atuendo> call, Throwable t) {
                Toast.makeText(getActivity(), "Hola", Toast.LENGTH_SHORT).show();
                t.printStackTrace();
            }
        };
    }
}
