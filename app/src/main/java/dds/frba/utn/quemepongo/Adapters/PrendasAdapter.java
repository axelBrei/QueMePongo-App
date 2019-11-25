package dds.frba.utn.quemepongo.Adapters;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

import dds.frba.utn.quemepongo.Controllers.PrendasController;
import dds.frba.utn.quemepongo.Helpers.ErrorHelper;
import dds.frba.utn.quemepongo.Helpers.RetrofitInstanciator;
import dds.frba.utn.quemepongo.Model.Prenda;
import dds.frba.utn.quemepongo.Model.WebServices.Error;
import dds.frba.utn.quemepongo.Model.WebServices.Request.Prendas.DeletePrendaRequest;
import dds.frba.utn.quemepongo.QueMePongo;
import dds.frba.utn.quemepongo.R;
import dds.frba.utn.quemepongo.Repository.PrendasRepository;
import dds.frba.utn.quemepongo.Utils.ActivityHelper;
import dds.frba.utn.quemepongo.Utils.CustomListenners.OnCompleteListenerWithStatusAndApplication;
import dds.frba.utn.quemepongo.Utils.CustomListenners.OnCompleteListenner;
import dds.frba.utn.quemepongo.View.Activity.DetallePrendaActivity;
import de.hdodenhof.circleimageview.CircleImageView;

public class PrendasAdapter extends RecyclerView.Adapter {

    private List<Prenda> prendas;
    private String idGuardarropa;
    private Activity activity;
    private int cellResource = R.layout.prenda_item_cell;
    private PrendasController prendasController;

    public PrendasAdapter(Activity context) {
        prendas = new ArrayList<>();
        this.activity = context;
        prendasController = new PrendasController(context);
    }

    public PrendasAdapter(Activity context, List<Prenda> prendas) {
        this.prendas = prendas;
        this.activity = context;
    }

    public PrendasAdapter(Activity activity, int cellResource){
        this.activity = activity;
        this.cellResource = cellResource;
    }

    public PrendasAdapter(Activity activity, int cellResource, List<Prenda> prendas){
        this.activity = activity;
        this.cellResource = cellResource;
        this.prendas = prendas;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(activity).inflate(cellResource,viewGroup, false);
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

    public void removeItem(int index, OnCompleteListenner<Void> onCompleteListenner, OnCompleteListenner<String> onFailListener){
        Prenda prendaAux = prendas.get(index);
        prendas.remove(prendaAux);
        notifyItemRemoved(index);
        showUndoSnackbar(prendaAux, index, onCompleteListenner, onFailListener);
    }

    private void showUndoSnackbar(Prenda prenda, int pos, OnCompleteListenner<Void> onCompleteListenner, OnCompleteListenner<String> onFailListener) {
        Snackbar snackbar = Snackbar.make(activity.findViewById(R.id.MainScreenContainer), "Deshacer", Snackbar.LENGTH_LONG);
        snackbar.setAction("Deshacer", v -> undoDelete(prenda, pos));
        snackbar.show();
        snackbar.addCallback(new Snackbar.Callback(){
            @Override
            public void onDismissed(Snackbar transientBottomBar, int event) {
                if (event != Snackbar.Callback.DISMISS_EVENT_ACTION) {
                    prendasController.eliminarPrenda(
                            prenda.getId(),
                            new OnCompleteListenerWithStatusAndApplication() {
                                @Override
                                public void onComplete(Boolean success, QueMePongo application, @Nullable Object obj) {
                                    if(success){
                                        onCompleteListenner.onComplete(null);
                                    }else {
                                        undoDelete(prenda, pos);
                                        onFailListener.onComplete( ((Error)obj).getMessage() );
                                    }

                                }
                            }
                    );
                }
                onCompleteListenner.onComplete(null);
            }
        });
    }

    private void undoDelete(Prenda prenda, int pos) {
        prendas.add(pos, prenda);
        notifyItemInserted(pos);
    }

    public String getIdGuardarropa() {
        return idGuardarropa;
    }

    public void setIdGuardarropa(String idGuardarropa) {
        this.idGuardarropa = idGuardarropa;
    }

    private class PrendasViewHolder extends RecyclerView.ViewHolder{
        private TextView description;
        private TextView colorP;
        private TextView colorS;
        private TextView comosition;
        private CircleImageView typeImage;
        private ImageView likeButton;
        private RelativeLayout container;

        public PrendasViewHolder(@NonNull View itemView) {
            super(itemView);
            description = itemView.findViewById(R.id.PrendaCellDescription);
            colorP = itemView.findViewById(R.id.PrendaCellPrimaryColor);
            colorS = itemView.findViewById(R.id.PrendaCellSecondryColor);
            comosition = itemView.findViewById(R.id.PrendaCellCompositionType);
            likeButton = itemView.findViewById(R.id.PrendasContainerLikeButton);
            typeImage = itemView.findViewById(R.id.PrendaCellTypeImage);
            container = itemView.findViewById(R.id.PrendaCellContainer);


        }

        public void fillView(Prenda prenda, int index){
            description.setText(prenda.getDescripcion());
            String primaryColor = prenda.getColorP();
            if(!prenda.getColorS().isEmpty()){
                primaryColor += " /";
            }

            colorP.setText(primaryColor);
            colorS.setText(prenda.getColorS());
            comosition.setText(prenda.getTipoDeTela());

            typeImage.setImageDrawable(
                    setTypeImageDrawable(
                            prenda.getTipoDePrenda()
                    )
            );
            if(cellResource == R.layout.prenda_item_cell){
                container.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("Prenda", prenda);
                        ActivityHelper.startActivity(activity, DetallePrendaActivity.class,bundle);
                    }
                });
            }
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
