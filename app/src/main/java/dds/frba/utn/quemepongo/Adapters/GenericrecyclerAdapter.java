package dds.frba.utn.quemepongo.Adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import dds.frba.utn.quemepongo.Utils.GenericViewHolder;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
public abstract class GenericrecyclerAdapter<T> extends RecyclerView.Adapter {
    Context context;
    Integer layoutId;
    List<T> list;

    public GenericrecyclerAdapter(Context context, Integer layoutId, @Nullable List<T> list) {
        this.layoutId = layoutId;
        this.list = list;
        if(list == null){
            this .list = new ArrayList<>();
        }
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return GenericViewHolder.createViewHolder(context, layoutId, viewGroup);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        ButterKnife.bind(viewHolder.itemView);
        fillView(viewHolder.itemView, list.get(i));

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void addItems(List<T> items){
        list.addAll(items);
        notifyDataSetChanged();
    }

    public void replaceItems(List<T> items){
        list.clear();
        list.addAll(items);
        notifyDataSetChanged();
    }

    public void removeItem(T item){
        list.remove(item);
        notifyDataSetChanged();
    }

    public abstract void fillView(View v,T item);
}
