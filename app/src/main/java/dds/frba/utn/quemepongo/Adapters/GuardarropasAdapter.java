package dds.frba.utn.quemepongo.Adapters;

import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import dds.frba.utn.quemepongo.Helpers.ListSwipeHelper;
import dds.frba.utn.quemepongo.Model.Guardarropa;
import dds.frba.utn.quemepongo.R;
import dds.frba.utn.quemepongo.Utils.Swipeable;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import me.grantland.widget.AutofitTextView;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class GuardarropasAdapter extends RecyclerView.Adapter {
    MutableLiveData<List<Guardarropa>> guardarropas;
    Context context;
    Swipeable swipeable;

    public GuardarropasAdapter(Context context, Swipeable swipeable) {
        guardarropas = new MutableLiveData<>();
        guardarropas.observeForever( (g) -> notifyDataSetChanged());
        this.context = context;
        this.swipeable = swipeable;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(context).inflate(R.layout.guardarropas_cell, viewGroup, false);
        return new GuardarropaViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        ((GuardarropaViewHolder) viewHolder).fillView(guardarropas.getValue().get(i));
    }

    @Override
    public int getItemCount() {
        return guardarropas.getValue().size();
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        new ItemTouchHelper(getSwipeHelper()).attachToRecyclerView(recyclerView);
        recyclerView.addItemDecoration(new DividerItemDecoration(context, LinearLayoutManager.VERTICAL));
    }

    private ListSwipeHelper getSwipeHelper() {
        return new ListSwipeHelper(context) {
            @Override
            public void onSwipe(int index, int direction) {
                Guardarropa guardarropa = guardarropas.getValue().get(index);
                switch (direction){
                    case ListSwipeHelper.LEFT: {
                        swipeable.onSwipeLeft(guardarropa);
                        break;
                    }
                    case ListSwipeHelper.RIGHT: {
                        swipeable.onSwipeRight(guardarropa);
                        break;
                    }
                }
            }

            @Override
            public ColorDrawable getColorDirection(int direction) {
                return new ColorDrawable( direction == ListSwipeHelper.LEFT ? Color.RED : context.getResources().getColor(R.color.shareGreen));
            }

            @Override
            public int getDirectionIcon(int dir) {
                return dir == ListSwipeHelper.LEFT ? R.drawable.ic_delete_gray_24dp : R.drawable.ic_share_gray_24dp;
            }
        };
    }

    //------------------------------ ACTIONABLE ------------------------------


    //------------------------------ VIEW HOLDER ------------------------------

    @FieldDefaults(level =  AccessLevel.PRIVATE)
    private class GuardarropaViewHolder extends RecyclerView.ViewHolder{
        AutofitTextView title;

        public GuardarropaViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.GuardarropaCellTitle);
        }

        public void fillView(Guardarropa guardarropa){
            title.setText(guardarropa.getDescripcion());
        }
    }

    //------------------------------ GETTER / SETTER ------------------------------

    public void setGuardarropas(List<Guardarropa> guardarropas) {
        this.guardarropas.setValue(guardarropas);
    }

    //------------------------------ LIST MODIFYERS ------------------------------
    public void removeItem(Guardarropa g) {
        List<Guardarropa> list = guardarropas.getValue();
        list.remove(g);
        guardarropas.setValue(list);
    }
}
