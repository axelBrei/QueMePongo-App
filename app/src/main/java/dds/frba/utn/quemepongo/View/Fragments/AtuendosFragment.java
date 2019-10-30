package dds.frba.utn.quemepongo.View.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import dds.frba.utn.quemepongo.Adapters.AtuendosAdapter;
import dds.frba.utn.quemepongo.QueMePongo;
import dds.frba.utn.quemepongo.R;
import dds.frba.utn.quemepongo.ViewModel.AtuendosViewModel;

public class AtuendosFragment extends Fragment {
    //UI
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

        atuendosRecyclerView = view.findViewById(R.id.atuendosFragmentRecyclerView);


        AtuendosAdapter adapter = new AtuendosAdapter(getActivity());
        DividerItemDecoration itemDecoration = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        atuendosRecyclerView.addItemDecoration(itemDecoration);
        atuendosRecyclerView.setAdapter(adapter);
        atuendosRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        atuendosViewModel.getAtuendo(this, guardarropa -> {
            adapter.setList(guardarropa.getAtuendos());
        });

        return view;
    }
}
