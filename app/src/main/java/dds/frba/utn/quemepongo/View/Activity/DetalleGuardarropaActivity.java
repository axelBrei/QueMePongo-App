package dds.frba.utn.quemepongo.View.Activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.LinearLayout;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import dds.frba.utn.quemepongo.Adapters.AtuendosAdapter;
import dds.frba.utn.quemepongo.Adapters.GenericrecyclerAdapter;
import dds.frba.utn.quemepongo.Adapters.PrendasAdapter;
import dds.frba.utn.quemepongo.Adapters.ViewPagerAdapter;
import dds.frba.utn.quemepongo.Controllers.GuardarropaController;
import dds.frba.utn.quemepongo.Model.Atuendo;
import dds.frba.utn.quemepongo.Model.Guardarropa;
import dds.frba.utn.quemepongo.Model.GuardarropasCompartido;
import dds.frba.utn.quemepongo.Model.Prenda;
import dds.frba.utn.quemepongo.R;
import dds.frba.utn.quemepongo.Utils.CustomListenners.OnCompleteListenerWithStatus;
import dds.frba.utn.quemepongo.View.Fragments.GenericListFragment;
import dds.frba.utn.quemepongo.View.QueMePongoActivity;
import me.grantland.widget.AutofitTextView;

public class DetalleGuardarropaActivity extends QueMePongoActivity {
    public static final String EXTRA_GUARDARROPA = "EXTRA_GUARDARROPA";

    @BindView(R.id.DetalleGuardarropaViewPager)
        ViewPager viewPager;

    @BindView(R.id.DetalleGuardarropaTabLayout)
        TabLayout viewPagerTabLayout;

    GuardarropaController controller;
    Guardarropa currentGuardarropa;

    GenericListFragment<GuardarropasCompartido> compartidoGenericListFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getIntent().getExtras();
        controller = new GuardarropaController(_activity);


        if (bundle != null) {
            currentGuardarropa = (Guardarropa) bundle.getSerializable(EXTRA_GUARDARROPA);
            controller.getGuardarropaCompleto(
                    currentGuardarropa.getId(),
                    (success, application, obj) -> {
                        if (success) {
                            currentGuardarropa = (Guardarropa) obj;
                            initializeViewPager(currentGuardarropa.getPrendas(), currentGuardarropa.getAtuendos());
                            controller.getCompartidos(
                                    currentGuardarropa.getId(),
                                    ((succed, o) -> {
                                        if(succed){
                                            List<GuardarropasCompartido> compartidos = ((List<GuardarropasCompartido>) o);
                                            compartidoGenericListFragment.addItems(compartidos);
                                        }
                                    })
                            );
                        }
                    }
            );
            setToolbarTitle(currentGuardarropa.getDescripcion());
        }
        enableBackButton();
    }

    private void initializeViewPager(List<Prenda> prendaList, List<Atuendo> atuendosList) {
        GenericListFragment<Prenda> prendaGenericListFragment = GenericListFragment.createFragment(new PrendasAdapter(_activity, prendaList));
        GenericListFragment<Atuendo> atuendoGenericrecyclerAdapter = GenericListFragment.createFragment(new AtuendosAdapter(_activity, atuendosList));
        compartidoGenericListFragment = GenericListFragment.createFragment(
                new GenericrecyclerAdapter(this, R.layout.compartido_cell, null) {
                    @Override
                    public void fillView(View v, Object item) {
                        AutofitTextView title = v.findViewById(R.id.compartidoCellTitle);
                        title.setText( ((GuardarropasCompartido)item).getNombreCompartido() );

                        v.findViewById(R.id.compartidoCellContainer).setOnClickListener(
                                view -> showAlertToDelete( (GuardarropasCompartido) item)
                        );
                    }
                }
        );

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager(),Arrays.asList(
                prendaGenericListFragment,
                atuendoGenericrecyclerAdapter,
                compartidoGenericListFragment
        )) {
            @Override
            public String getItemTitle(int position) {
                switch (position){
                    case 0: return "Prendas";
                    case 1: return "Atuendos";
                    case 2: return "Compartidos";
                    default: return "";
                }
            }
        };

        viewPagerTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        viewPagerTabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        viewPagerTabLayout.setupWithViewPager(viewPager);
        adapter.setTabsTitles(viewPagerTabLayout);
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(0);
    }

    private void createCompartidosFragment() {

    }

    // -------------------- SCREEN CONFIGS --------------------
    @Override
    protected boolean enableToolbarSpinner() {
        return false;
    }

    @Override
    protected int getView() {
        return R.layout.activity_detalle_guardarropa;
    }
    // ---------------------------------------------------------


    private void showAlertToDelete(GuardarropasCompartido guardarropasCompartido){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Aviso");
        builder.setMessage("Desea dejar de comparir el guardarropas a esta persona?");

        // add the buttons
        builder.setPositiveButton("Dejar de compartir", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                controller.dejarDeCompartirGuardarropa(
                        currentGuardarropa.getId(),
                        guardarropasCompartido.getUidCompartido(),
                        (succed, obj) -> {
                            if(succed){
                                compartidoGenericListFragment.removeItem(guardarropasCompartido);
                            }
                        }
                );
            }
        });
        builder.setNegativeButton("Cancelar", null);

        // create and show the alert dialog
        builder.create().show();
    }

}
