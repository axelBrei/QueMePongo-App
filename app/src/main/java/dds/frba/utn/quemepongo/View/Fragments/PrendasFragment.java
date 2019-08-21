package dds.frba.utn.quemepongo.View.Fragments;


import android.arch.lifecycle.ViewModelProviders;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
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

import dds.frba.utn.quemepongo.Adapters.PrendasAdapter;
import dds.frba.utn.quemepongo.Helpers.ListSwipeHelper;
import dds.frba.utn.quemepongo.R;
import dds.frba.utn.quemepongo.Utils.ActivityHelper;
import dds.frba.utn.quemepongo.View.Activity.CrearPrendasActivity;
import dds.frba.utn.quemepongo.ViewModel.PrendasViewModel;

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
                        if (guardarropa.getPrendas() != null) {
                        eventsInterface.setSpinnerItem(prendasViewModel.getApplication().getGuardarropas().getValue().indexOf(guardarropa));
                        prendasViewModel.setPrendas(guardarropa.getPrendas());
                        adapter.setIdGuardarropa(String.valueOf(guardarropa.getId()));
                    }

                }
        );
        prendasViewModel.getPrendas().observe(
                getActivity(),
                prendas -> adapter.addItems(prendas)
        );


        agregarPrendaFAB.setOnClickListener( (View v) -> {
            // CARGO FRAGMENT PARA CREAR PRENDA
            ActivityHelper.startActivity(getActivity(), CrearPrendasActivity.class);
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
        new ItemTouchHelper(new ListSwipeHelper<PrendasAdapter>(getContext()){
            @Override
            public int enableDirections() {
                return ListSwipeHelper.LEFT;
            }

            @Override
            public ColorDrawable getColorDirection(int direction) {
                return new ColorDrawable(Color.RED);
            }

            @Override
            public int getDirectionIcon(int dir) {
                return R.drawable.ic_delete_gray_24dp;
            }

            @Override
            public void onSwipe(int index, int direction) {
                if(direction == ListSwipeHelper.LEFT){
                    eventsInterface.onLoading(true);
                    // TODO: handle errors
                    adapter.removeItem(index,
                            // on succes
                            param -> eventsInterface.onLoading(false),
                            // on fail
                            error -> {
                                eventsInterface.onLoading(false);
                                Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show();
                            });
                }
            }
        }).attachToRecyclerView(prendasRecyclerView);
    }

    public interface EventsInterface {
        void onLoading(Boolean isLoading);
        void setSpinnerItem(int index);
    }


}
