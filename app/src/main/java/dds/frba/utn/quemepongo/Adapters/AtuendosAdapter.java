package dds.frba.utn.quemepongo.Adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.aakira.expandablelayout.ExpandableLayout;

import java.util.ArrayList;
import java.util.List;

import dds.frba.utn.quemepongo.Model.Atuendo;
import dds.frba.utn.quemepongo.R;

public class AtuendosAdapter extends RecyclerView.Adapter {
    private Activity activity;
    private List<Atuendo> atuendoList = new ArrayList<>();
    private OnAtuendoClick clickListener;

    public AtuendosAdapter(Activity context) {
        this.activity = context;
    }

    public AtuendosAdapter(Activity context, List<Atuendo> atuendoList) {
        this.activity = context;
        this.atuendoList = atuendoList;
    }


    public AtuendosAdapter(Activity context, OnAtuendoClick clickListener) {
        this.activity = context;
        this.atuendoList = atuendoList;
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(activity).inflate(R.layout.atuendo_item_cell, viewGroup, false);
        return new AtuendoViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        AtuendoViewHolder holder = (AtuendoViewHolder) viewHolder;
        holder.fillView(atuendoList.get(i), i);
        if(clickListener != null){
            holder.setClickListener(clickListener, atuendoList.get(i));
        }
    }

    @Override
    public int getItemCount() {
        return atuendoList.size();
    }

    public void addAtuendo(Atuendo atuendo){
        atuendoList.add(atuendo);
        notifyDataSetChanged();
    }

    public void addAtuendos(List<Atuendo> atuendos){
        atuendoList.addAll(atuendos);
        notifyDataSetChanged();
    }

    public void setList(List<Atuendo> atuendos){
        if(atuendos != null){
            atuendoList.clear();
            atuendoList.addAll(atuendos);
            notifyDataSetChanged();
        }
    }

    private class AtuendoViewHolder extends RecyclerView.ViewHolder{
        private ExpandableLayout expandableLayout;
        private LinearLayout container;
        private TextView title;
        private RecyclerView prendarRecycler;
        private ImageView imageTitulo;

        public AtuendoViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.atuendoCellTitulo);
            expandableLayout = itemView.findViewById(R.id.atuendoCellExpandableLayout);
            imageTitulo = itemView.findViewById(R.id.atuendoCellTituloImage);
            prendarRecycler = itemView.findViewById(R.id.atuendosCellPrendasRecycler);
            container = itemView.findViewById(R.id.atuendoCellContainer);
            expandableLayout.collapse();
        }

        public void fillView(Atuendo a, Integer index){
            title.setText("Atuendo #" + (index + 1));
            if(index == 0)
                expandableLayout.setExpanded(true);
            toggleImage();


            container.setOnClickListener( v -> {
                expandableLayout.toggle();
                toggleImage();
            });

            PrendasAdapter adapter = new PrendasAdapter(activity, R.layout.prenda_grid_item_cell,a.getPrendas());
            prendarRecycler.setLayoutManager(new LinearLayoutManager(activity, RecyclerView.VERTICAL, false));
            prendarRecycler.setAdapter(adapter);

        }

        private void toggleImage(){
            Integer imageRes;
            try{
                imageRes = (Integer) imageTitulo.getTag() == R.drawable.ic_arrow_up ? R.drawable.ic_arrow_down : R.drawable.ic_arrow_up;
            }catch (NullPointerException e){
                imageRes = R.drawable.ic_arrow_up;
            }
            imageTitulo.setImageDrawable(activity.getResources().getDrawable(imageRes));
            imageTitulo.setTag(imageRes);
        }
        public void setClickListener(OnAtuendoClick listener, Atuendo atuendo){
            container.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onClick(atuendo);
                }
            });
        }
    }

    public interface OnAtuendoClick {
        void onClick(Atuendo a);
    }
}
