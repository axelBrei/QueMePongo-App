package dds.frba.utn.quemepongo.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import dds.frba.utn.quemepongo.Helpers.CustomRetrofitCallback;
import dds.frba.utn.quemepongo.Model.Prenda;
import dds.frba.utn.quemepongo.R;
import de.hdodenhof.circleimageview.CircleImageView;

public class PrendasAdapter extends RecyclerView.Adapter {

    private List<Prenda> prendas;
    private Activity activity;

    public PrendasAdapter(Activity context) {
        prendas = new ArrayList<>();
        this.activity = context;
    }

    public PrendasAdapter(List<Prenda> prendas, Activity context) {
        this.prendas = prendas;
        this.activity = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(activity).inflate(R.layout.prenda_item_cell,viewGroup, false);
        return new PrendasViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        PrendasViewHolder prendasViewHolder = (PrendasViewHolder)viewHolder;
        prendasViewHolder.fillView(prendas.get(i), i);
        prendasViewHolder.setClickListenner(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(activity, "Agregado a favoritos", Toast.LENGTH_SHORT).show();
            }
        });
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

    public void removeItem(int index, CustomRetrofitCallback<Object> callback){
        Prenda prendaAux = prendas.get(index);
        prendas.remove(prendaAux);
        notifyItemRemoved(index);
        showUndoSnackbar(prendaAux, index);
        // TODO: usar callback para habdlear la respuesta del back
    }

    private void showUndoSnackbar(Prenda prenda, int pos) {
        Snackbar snackbar = Snackbar.make(activity.findViewById(R.id.MainScreenContainer), "Deshacer", Snackbar.LENGTH_LONG);
        snackbar.setAction("Deshacer", v -> undoDelete(prenda, pos));
        snackbar.show();
        snackbar.addCallback(new Snackbar.Callback(){
            @Override
            public void onDismissed(Snackbar transientBottomBar, int event) {
                if (event == Snackbar.Callback.DISMISS_EVENT_TIMEOUT) {
                    // TODO: pedido al back para que borre la prenda, en caso de que falle se vuelve a agregar a la lista con undo delete.
                    Toast.makeText(activity, "DELETE ITSELF", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void undoDelete(Prenda prenda, int pos) {
        prendas.add(pos, prenda);
        notifyItemInserted(pos);
    }

    private class PrendasViewHolder extends RecyclerView.ViewHolder{
        private TextView description;
        private TextView colorP;
        private TextView colorS;
        private TextView comosition;
        private CircleImageView typeImage;
        private ImageView likeButton;

        public PrendasViewHolder(@NonNull View itemView) {
            super(itemView);
            description = itemView.findViewById(R.id.PrendaCellDescription);
            colorP = itemView.findViewById(R.id.PrendaCellPrimaryColor);
            colorS = itemView.findViewById(R.id.PrendaCellSecondryColor);
            comosition = itemView.findViewById(R.id.PrendaCellCompositionType);
            likeButton = itemView.findViewById(R.id.PrendasContainerLikeButton);
            typeImage = itemView.findViewById(R.id.PrendaCellTypeImage);
        }

        public void fillView(Prenda prenda, int index){
            description.setText(prenda.getDescripcion());
            colorP.setText(prenda.getColorP());
            colorS.setText(prenda.getColorS());
            comosition.setText(prenda.getTipoDeTela());

            typeImage.setImageDrawable(
                    setTypeImageDrawable(
                            prenda.getClass().getName().split("TiposPrenda.")[1]
                    )
            );
        }
        
        public void setClickListenner(View.OnClickListener listenner){
            likeButton.setOnClickListener(listenner);
        }

        private Drawable setTypeImageDrawable(String prendaType){
            Resources res = activity.getResources();
            switch (prendaType){
                case "Superior":
                    return res.getDrawable(R.drawable.ic_remera);
                case "Inferior":
                    return res.getDrawable(R.drawable.ic_pantalones);
                case "Accesorios":
                    return res.getDrawable(R.drawable.ic_accesory);
                case "Calzado":
                    return res.getDrawable(R.drawable.ic_shoe);
                default: return null;
            }
        }

    }
}
