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

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

import dds.frba.utn.quemepongo.Helpers.CustomRetrofitCallback;
import dds.frba.utn.quemepongo.Helpers.RetrofitInstanciator;
import dds.frba.utn.quemepongo.Model.Prenda;
import dds.frba.utn.quemepongo.Model.WebServices.PrendaRequestObject;
import dds.frba.utn.quemepongo.Model.WebServices.Request.Prendas.DeletePrendaRequest;
import dds.frba.utn.quemepongo.R;
import dds.frba.utn.quemepongo.Repository.PrendasRepository;
import dds.frba.utn.quemepongo.Utils.OnCompleteListenner;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Response;

public class PrendasAdapter extends RecyclerView.Adapter {

    private List<Prenda> prendas;
    private String idGuardarropa;
    private Activity activity;
    private int cellResource = R.layout.prenda_item_cell;
    private PrendasRepository repository = RetrofitInstanciator.getInstance().getRetrofit().create(PrendasRepository.class);

    public PrendasAdapter(Activity context) {
        prendas = new ArrayList<>();
        this.activity = context;
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
                    repository.eliminarPrenda(
                            new DeletePrendaRequest(
                                    FirebaseAuth.getInstance().getCurrentUser().getUid(),
                                    getIdGuardarropa(),
                                    new PrendaRequestObject(prenda)
                            )
                    ).enqueue(new CustomRetrofitCallback<Void>() {
                        @Override
                        public void onCustomResponse(Call<Void> call, Response<Void> response) {
                            onCompleteListenner.onComplete(response.body());
                        }

                        @Override
                        public void onCustomFailure(Call<Void> call, Error error) {
                            undoDelete(prenda,pos);
                            onFailListener.onComplete(error.getMessage());
                        }

                        @Override
                        public void onHttpRequestFail(Call<Void> call, Throwable t) {
                            undoDelete(prenda,pos);
                            onFailListener.onComplete("Ha ocurrido un error inesperado");
                        }
                    });
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
            String primaryColor = prenda.getColorP();
            if(!prenda.getColorS().isEmpty()){
                primaryColor += " /";
            }

            colorP.setText(primaryColor);
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
