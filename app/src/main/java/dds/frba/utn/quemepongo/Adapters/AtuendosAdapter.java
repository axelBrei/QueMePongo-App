package dds.frba.utn.quemepongo.Adapters;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.aakira.expandablelayout.ExpandableLayout;

import java.util.ArrayList;
import java.util.List;

import dds.frba.utn.quemepongo.Model.Atuendo;
import dds.frba.utn.quemepongo.Model.Prenda;
import dds.frba.utn.quemepongo.Model.TiposPrenda.Accesorios;
import dds.frba.utn.quemepongo.Model.TiposPrenda.Calzado;
import dds.frba.utn.quemepongo.Model.TiposPrenda.Inferior;
import dds.frba.utn.quemepongo.Model.TiposPrenda.Superior;
import dds.frba.utn.quemepongo.R;

public class AtuendosAdapter extends RecyclerView.Adapter {
    private Activity activity;
    private List<Atuendo> atuendoList = new ArrayList<>();

    public AtuendosAdapter(Activity context) {
        this.activity = context;
    }

    public AtuendosAdapter(Activity context, List<Atuendo> atuendoList) {
        this.activity = context;
        this.atuendoList = atuendoList;
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
    }

    @Override
    public int getItemCount() {
        return atuendoList.size();
    }

    public void addAtuendo(Atuendo atuendo){
        atuendoList.add(atuendo);
        notifyDataSetChanged();
    }

    public void setList(List<Atuendo> atuendos){
        atuendoList.clear();
        atuendoList.addAll(atuendos);
        notifyDataSetChanged();
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
            prendarRecycler.setLayoutManager(new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false));
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

    }
}
