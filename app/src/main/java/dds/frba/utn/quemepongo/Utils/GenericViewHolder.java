package dds.frba.utn.quemepongo.Utils;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.function.Function;

import lombok.Getter;
import lombok.Setter;

public abstract  class GenericViewHolder<T> extends RecyclerView.ViewHolder {

    public GenericViewHolder(@NonNull View itemView) {
        super(itemView);
    }

    public static RecyclerView.ViewHolder createViewHolder(Context context, Integer resourceId, ViewGroup viewGroup){
        View v = LayoutInflater.from(context).inflate(resourceId, viewGroup, false);
        return new RecyclerView.ViewHolder(v){};
    }

    public abstract void fillView(T item);
}
