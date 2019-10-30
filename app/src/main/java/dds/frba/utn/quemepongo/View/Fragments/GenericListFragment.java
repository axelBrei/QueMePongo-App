package dds.frba.utn.quemepongo.View.Fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import dds.frba.utn.quemepongo.Adapters.GenericrecyclerAdapter;
import dds.frba.utn.quemepongo.R;
import lombok.Setter;

public class GenericListFragment<T> extends Fragment {

    @BindView(R.id.GenericListRecyclerView)
    RecyclerView recyclerView;


    @Setter
    RecyclerView.Adapter adapter;

    public GenericListFragment() {
        // Required empty public constructor
    }

    public static GenericListFragment createFragment(RecyclerView.Adapter adapter){
        GenericListFragment fragment = new GenericListFragment();
        fragment.setAdapter(adapter);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_generic_list, container, false);
        ButterKnife.bind(this,view);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(),RecyclerView.VERTICAL, false);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);

        return view;
    }

    public void addItems(List<T> items){
        if(adapter.getClass().getSuperclass().equals(GenericrecyclerAdapter.class)){
            ((GenericrecyclerAdapter)adapter).addItems(items);
        }
    }

    public void removeItem(T item){
        if(adapter.getClass().getSuperclass().equals(GenericrecyclerAdapter.class)){
            ((GenericrecyclerAdapter)adapter).removeItem(item);
        }
    }

}
