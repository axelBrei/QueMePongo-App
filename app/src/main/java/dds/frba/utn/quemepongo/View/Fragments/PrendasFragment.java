package dds.frba.utn.quemepongo.View.Fragments;


import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import dds.frba.utn.quemepongo.Adapters.PrendasAdapter;
import dds.frba.utn.quemepongo.Helpers.CustomRetrofitCallback;
import dds.frba.utn.quemepongo.Helpers.SwipeToDeleteHelper;
import dds.frba.utn.quemepongo.Model.Prenda;
import dds.frba.utn.quemepongo.R;
import dds.frba.utn.quemepongo.View.Activity.CrearPrendasActivity;
import dds.frba.utn.quemepongo.ViewModel.PrendasViewModel;
import retrofit2.Call;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class PrendasFragment extends Fragment {
    // MODEL
    private PrendasViewModel prendasViewModel;
    // UI
    private TextView tienePrendasEditText;
    private RecyclerView prendasRecyclerView;
    // INTERFACES
    private EventsInterface eventsInterface;


    public PrendasFragment() {
        // Required empty public constructor
    }

    public static PrendasFragment newInstance(EventsInterface eventsInterface){
        PrendasFragment fragment = new PrendasFragment();
        fragment.setEventsInterface(eventsInterface);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_prendas, container, false);

        prendasRecyclerView = view.findViewById(R.id.PrendasFragmentRecyclerView);
        FloatingActionButton agregarPrendaFAB = view.findViewById(R.id.PrendasFragmentFloatinActionButton);
        tienePrendasEditText = view.findViewById(R.id.MainActivityTienePrendas);

        prendasViewModel = ViewModelProviders.of(getActivity()).get(PrendasViewModel.class);
        // PREPARO LISTA PARA QUE SE MUESTRE EN LA PANTALLA
        PrendasAdapter adapter = new PrendasAdapter(getActivity());
        prendasRecyclerView.setAdapter(adapter);
        prendasRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayout.VERTICAL,false));
        prendasRecyclerView.addItemDecoration(setItemDecorator());
        attachItemTouchHelper(adapter);

        // CREO OBSERVERS PARA QUE SE LLENEN LAS LISTAS
        prendasViewModel.getApplication().getGuardarropaActual().observe(
                getActivity(),
                guardarropa -> {
                    eventsInterface.setSpinnerItem(prendasViewModel.getApplication().getGuardarropas().indexOf(guardarropa));
                    prendasViewModel.setPrendas(guardarropa.getPrendas());
                }
        );
        prendasViewModel.getPrendas().observe(
                getActivity(),
                prendas -> adapter.addItems(prendas)
        );


        agregarPrendaFAB.setOnClickListener( (View v) -> {
            // CARGO FRAGMENT PARA CREAR PRENDA
            Intent intent = new Intent(getContext(), CrearPrendasActivity.class);
            startActivity(intent);
        });
        return view;
    }

    public void setEventsInterface(EventsInterface eventsInterface) {
        this.eventsInterface = eventsInterface;
    }

    private RecyclerView.ItemDecoration setItemDecorator(){
        return new RecyclerView.ItemDecoration() {
            @Override
            public void onDraw(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                int left = parent.getLeft() + 200;
                int right = parent.getRight();
                Drawable divider = getActivity().getResources().getDrawable(R.drawable.custom_list_divider);
                int childCount = parent.getChildCount();
                for (int i = 0; i < childCount; i++) {
                    View child = parent.getChildAt(i);

                    RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();

                    int top = child.getBottom() + params.bottomMargin;
                    int bottom = top + divider.getIntrinsicHeight();

                    divider.setBounds(left, top, right, bottom);
                    divider.draw(c);
                }
            }

        };
    }

    private void attachItemTouchHelper(PrendasAdapter adapter){
        new ItemTouchHelper(new SwipeToDeleteHelper<PrendasAdapter>(adapter, getContext()){
            @Override
            public void adapterRemoveItem(int index) {
                eventsInterface.onLoading(true);
                // TODO: handle errors
                adapter.removeItem(index, new CustomRetrofitCallback<Object>() {
                    @Override
                    public void onCustomResponse(Call<Object> call, Response<Object> response) {
                        eventsInterface.onLoading(false);
                    }

                    @Override
                    public void onCustomFailure(Call<Object> call, Error error) {
                        eventsInterface.onLoading(false);
                    }

                    @Override
                    public void onHttpRequestFail(Call<Object> call, Throwable t) {
                        eventsInterface.onLoading(false);
                    }
                });
            }
        }).attachToRecyclerView(prendasRecyclerView);
    }

    public interface EventsInterface {
        void onLoading(Boolean isLoading);
        void setSpinnerItem(int index);
    }


}
