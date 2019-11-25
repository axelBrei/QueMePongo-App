package dds.frba.utn.quemepongo.View.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import butterknife.BindView;
import dds.frba.utn.quemepongo.Model.Prenda;
import dds.frba.utn.quemepongo.R;
import dds.frba.utn.quemepongo.View.QueMePongoActivity;
import me.grantland.widget.AutofitTextView;

public class DetallePrendaActivity extends QueMePongoActivity {

    @BindView(R.id.DetallePrendaTipoDeTela)
    AutofitTextView tipoDeTela;
    @BindView(R.id.DetallePrendaDescripcion)
    AutofitTextView descripcion;
    @BindView(R.id.DetallePrendaColorP)
    AutofitTextView colorP;
    @BindView(R.id.DetallePrendaColorS)
    AutofitTextView colorS;
    @BindView(R.id.DetallePrendaFormalidad)
    AutofitTextView formalidad;
    @BindView(R.id.DetallePrendaABrigo)
    AutofitTextView abrigo;
    @BindView(R.id.DetallePrendaTipoDePrenda)
    AutofitTextView tipoDePrenda;

    @BindView(R.id.DetallePrendaFoto)
    ImageView foto;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        enableBackButton();
        setToolbarTitle("Prendas");
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if(bundle != null){
            Prenda prenda = (Prenda) bundle.getSerializable("Prenda");
            descripcion.setText("Nombre: " + prenda.getDescripcion());
            tipoDePrenda.setText("Tipo de Prenda: " + prenda.getTipoDePrenda());
            tipoDeTela.setText("Tipo de Tela: " + prenda.getTipoDeTela());
            colorP.setText("Color Primario: "+prenda.getColorP());
            colorS.setText("Color Secundario: " + prenda.getColorS());
            Double a = prenda.getAbrigo() / 9.5;
            abrigo.setText("Abrigo: " + a.toString());
            formalidad.setText("Formalidad: " + prenda.getFormalidad());
//
//            Picasso
//                    .get()
//                    .load(prenda.getFotoURL())
//                    .placeholder(R.drawable.ic_cloud_off_black_24dp)
//                    .into(foto);
        }
    }

    @Override
    protected int getView() {
        return R.layout.activity_detalle_prenda;
    }

    @Override
    protected boolean enableToolbarSpinner() {
        return false;
    }
}
