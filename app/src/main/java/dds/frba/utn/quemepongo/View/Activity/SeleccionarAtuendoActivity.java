package dds.frba.utn.quemepongo.View.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.github.javiersantos.materialstyleddialogs.MaterialStyledDialog;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import dds.frba.utn.quemepongo.Adapters.AtuendosAdapter;
import dds.frba.utn.quemepongo.Controllers.AtuendosController;
import dds.frba.utn.quemepongo.Model.Atuendo;
import dds.frba.utn.quemepongo.R;
import dds.frba.utn.quemepongo.Utils.ActivityHelper;
import dds.frba.utn.quemepongo.Utils.CustomListenners.OnCompleteListenerWithStatus;
import dds.frba.utn.quemepongo.View.QueMePongoActivity;

public class SeleccionarAtuendoActivity extends QueMePongoActivity {

    @BindView(R.id.SeleccionarAtuendoRecyclerView)
        RecyclerView atuendosRecycler;

    AtuendosController controller;
    AtuendosAdapter adapter;
    Long idEvento;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        idEvento = intent.getLongExtra("idEvento", -1);

        enableBackButton();
        setToolbarTitle("Seleccion de atuendo");

        initRecyclerView();
        if(idEvento > 0){
            fetchAtuendos(idEvento);
        }
    }

    private void initRecyclerView() {
        adapter = new AtuendosAdapter(_activity, new AtuendosAdapter.OnAtuendoClick() {
            @Override
            public void onClick(Atuendo a) {
                new MaterialStyledDialog.Builder(_activity)
                        .setCancelable(true)
                        .setHeaderColor(R.color.colorPrimarylight)
                        .setDescription("Quieres seleccionar este atuendo para el evento?")
                        .setHeaderScaleType(ImageView.ScaleType.FIT_CENTER)
                        .setHeaderDrawable(R.drawable.ic_error_white)
                        .setPositiveText("Aceptar")
                        .onPositive((dialog, which) -> {
                            reservarAtuendo(a);
                        }).setNegativeText("Cancelar")
                        .build()
                        .show();
            }
        });
        atuendosRecycler.setAdapter(adapter);
        atuendosRecycler.setLayoutManager(new LinearLayoutManager(_activity, RecyclerView.VERTICAL, false));
    }

    private void reservarAtuendo(Atuendo atuendo){
        controller.reservarAtuendo(
                atuendo,
                idEvento,
                (succed, obj) -> {
                    if(succed){
                        Toast.makeText(_activity, "Gracias por seleccionar el atuendo", Toast.LENGTH_SHORT).show();
                    }
                }
        );
        onBackPressed();
    }

    private void fetchAtuendos(Long eventId){
        controller = new AtuendosController(_activity);

        controller.getAtuendosDelEvento(eventId, new OnCompleteListenerWithStatus<List<Atuendo>>() {
            @Override
            public void onComplete(Boolean succed, List<Atuendo> obj) {
                if(succed){
                    adapter.addAtuendos(obj);
                }
            }
        });
    }

    @Override
    protected int getView() {
        return R.layout.activity_seleccionar_atuendo;
    }

    @Override
    protected boolean enableToolbarSpinner() {
        return false;
    }

    @Override
    public void onBackPressed() {
        ActivityHelper.startActivity(_activity, MainActivity.class);
    }
}
