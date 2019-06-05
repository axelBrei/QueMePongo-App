package dds.frba.utn.quemepongo.View.Fragments;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import com.google.firebase.auth.FirebaseAuth;

import dds.frba.utn.quemepongo.Adapters.AtuendosAdapter;
import dds.frba.utn.quemepongo.Helpers.CustomRetrofitCallback;
import dds.frba.utn.quemepongo.Helpers.ErrorHelper;
import dds.frba.utn.quemepongo.Model.Atuendo;
import dds.frba.utn.quemepongo.Model.WebServices.Request.Atuendo.GetAtuendosRequest;
import dds.frba.utn.quemepongo.QueMePongo;
import dds.frba.utn.quemepongo.R;
import dds.frba.utn.quemepongo.ViewModel.AtuendosViewModel;

import retrofit2.Call;
import retrofit2.Response;

public class AtuendosFragment extends Fragment {
    //UI
    private FloatingActionButton generarAtuendoButton;
    private RecyclerView atuendosRecyclerView;
    //MODEL
    private AtuendosViewModel atuendosViewModel;
    private QueMePongo application;
    public AtuendosFragment() {
        // Required empty public constructor
    }

    public static AtuendosFragment newInstance(){
        AtuendosFragment fragment = new AtuendosFragment();

        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_atuendos, container, false);

        this.atuendosViewModel = ViewModelProviders.of(getActivity()).get(AtuendosViewModel.class);
        this.application = (QueMePongo) getActivity().getApplication();

        generarAtuendoButton = view.findViewById(R.id.atuendosFragmentFAB);
        atuendosRecyclerView = view.findViewById(R.id.atuendosFragmentRecyclerView);

        generarAtuendoButton.setOnClickListener( v -> fetchAtuendos());

        AtuendosAdapter adapter = new AtuendosAdapter(getActivity());
        DividerItemDecoration itemDecoration = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        atuendosRecyclerView.addItemDecoration(itemDecoration);
        atuendosRecyclerView.setAdapter(adapter);
        atuendosRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        atuendosViewModel.getAtuendo(this, atuendo -> {
            adapter.setList(atuendo);
        });

        return view;
    }

    private void fetchAtuendos(){
        GetAtuendosRequest request = new GetAtuendosRequest(
                FirebaseAuth.getInstance().getCurrentUser().getUid(),
                atuendosViewModel.getApplication().getGuardarropaActual().getValue().getId()
        );
        Call<Atuendo> call = atuendosViewModel.getAtuendosRespository().getAtuendoRecomendado(request);
        application.loading.setValue(true);
        new Thread(() -> call.enqueue(new CustomRetrofitCallback<Atuendo>() {
            @Override
            public void onCustomResponse(Call<Atuendo> call1, Response<Atuendo> response) {
                atuendosViewModel.addAtuendo(response.body());
                application.loading.setValue(false);
            }

            @Override
            public void onCustomFailure(Call<Atuendo> call1, Error error) {
                ErrorHelper.ShowSimpleError(getActivity(), error.getMessage());
                application.loading.setValue(false);
            }

            @Override
            public void onHttpRequestFail(Call<Atuendo> call1, Throwable t) {
                ErrorHelper.ShowSimpleError(getActivity(), t.getMessage());
                application.loading.setValue(false);
            }
        })).run();

    }
}