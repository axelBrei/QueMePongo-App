package dds.frba.utn.quemepongo.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import dds.frba.utn.quemepongo.Model.Prenda;
import dds.frba.utn.quemepongo.R;

public class PrendasAdapter extends RecyclerView.Adapter {

    private List<Prenda> prendas;
    private Context context;

    public PrendasAdapter(Context context) {
        prendas = new ArrayList<>();
        this.context = context;
    }

    public PrendasAdapter(List<Prenda> prendas, Context context) {
        this.prendas = prendas;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.prenda_item_cell,viewGroup, false);
        return new PrendasViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        PrendasViewHolder prendasViewHolder = (PrendasViewHolder)viewHolder;
        prendasViewHolder.fillView(prendas.get(i));
    }

    @Override
    public int getItemCount() {
        return prendas.size();
    }

    public void addItems(List<Prenda> nuevasPrendas){
        prendas.clear();
        prendas.addAll(nuevasPrendas);
        this.notifyDataSetChanged();
    }

    private class PrendasViewHolder extends RecyclerView.ViewHolder{
        private TextView description;
        private TextView colorP;
        private TextView colorS;
        private TextView comosition;
        private TextView usedIn;

        public PrendasViewHolder(@NonNull View itemView) {
            super(itemView);

            description = itemView.findViewById(R.id.PrendaCellDescription);
            colorP = itemView.findViewById(R.id.PrendaCellPrimaryColor);
            colorS = itemView.findViewById(R.id.PrendaCellSecondryColor);
            comosition = itemView.findViewById(R.id.PrendaCellCompositionType);
            usedIn = itemView.findViewById(R.id.PrendaCellUsedIn);
        }

        public void fillView(Prenda prenda){
            description.setText(prenda.getDescripcion());
            colorP.setText(prenda.getColorP());
            colorS.setText(prenda.getColorS());
            comosition.setText(prenda.getTipoDeTela());
            usedIn.setText(prenda.getClass().getName().split("TiposPrenda.")[1]);
        }

    }
}
