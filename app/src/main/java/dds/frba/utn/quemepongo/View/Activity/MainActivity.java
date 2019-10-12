package dds.frba.utn.quemepongo.View.Activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.iid.FirebaseInstanceId;

import java.io.IOException;

import butterknife.BindView;
import dds.frba.utn.quemepongo.Adapters.ViewPagerAdapter;
import dds.frba.utn.quemepongo.Controllers.ClienteController;
import dds.frba.utn.quemepongo.Helpers.CustomNotificationManager;
import dds.frba.utn.quemepongo.Helpers.RetrofitInstanciator;
import dds.frba.utn.quemepongo.Model.Evento;
import dds.frba.utn.quemepongo.R;
import dds.frba.utn.quemepongo.Repository.ClienteRepository;
import dds.frba.utn.quemepongo.Services.FirebaseMessagingService;
import dds.frba.utn.quemepongo.Utils.ActivityHelper;
import dds.frba.utn.quemepongo.View.Fragments.AtuendosFragment;
import dds.frba.utn.quemepongo.View.Fragments.PrendasFragment;
import dds.frba.utn.quemepongo.View.Fragments.QRFragment;
import dds.frba.utn.quemepongo.View.QueMePongoActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends QueMePongoActivity  implements PrendasFragment.EventsInterface, TabLayout.OnTabSelectedListener, NavigationView.OnNavigationItemSelectedListener{
    // UI
    @BindView(R.id.MainScreenViewPager)
        ViewPager viewPager;
    @BindView(R.id.MainScreenTabLayout)
        TabLayout tabLayout;
    @BindView(R.id.MainActivityScreen)
        DrawerLayout drawerLayout;
    @BindView(R.id.NavigationDrawer)
        NavigationView sideMenuView;

    private ActionBarDrawerToggle drawerToggle;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initTabs();
        initSideMenu();
        sendFirebaseToken();
    }

    private void sendFirebaseToken() {
        ClienteController controller = new ClienteController(this);
        controller.actualizarFCMToken(FirebaseAuth.getInstance().getUid());
    }

    private void initSideMenu(){
        drawerToggle = new ActionBarDrawerToggle(_activity, drawerLayout, R.string.openDrawer, R.string.closeDrawer);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();
        sideMenuView.setNavigationItemSelectedListener(this);
        findViewById(R.id.sideMenuLogoutButton).setOnClickListener( v -> {
            logout();
            drawerLayout.closeDrawers();
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        loadSideMenuData();
    }

    private void loadSideMenuData(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        View v = sideMenuView.getHeaderView(0);
        ((TextView) v.findViewById(R.id.sideMenuHeaderMail)).setText(user.getEmail());
        ((TextView) v.findViewById(R.id.sideMenuHeaderUsername)).setText(user.getDisplayName());
    }
    private void initTabs(){
        ViewPagerAdapter adapter = new ViewPagerAdapter(
                getSupportFragmentManager(),
                PrendasFragment.newInstance(this),
                AtuendosFragment.newInstance()
        ) {
            @Override
            public String getItemTitle(int position) {
                switch (position){
                    case 0: return "Prendas";
                    case 1: return "Atuendos";
                    default: return "";
                }
            }
        };
        tabLayout.addOnTabSelectedListener(this);
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tabLayout.setupWithViewPager(viewPager);
        adapter.setTabsTitles(tabLayout);
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(0);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case R.id.sideMenuShareQr: {
                ActivityHelper.showFragment(this, new QRFragment(), R.id.MainActivityScreen);
                break;
            }
            case R.id.sideMenuCrearGuardarropa: {
                ActivityHelper.startActivity(_activity, CrearGuardarropaActivity.class);
                break;
            }
            case R.id.sideMenuNotificacion:{
                CustomNotificationManager.showNotification(this);
            }
            case R.id.sideMenuVerGuardarropas: {
                ActivityHelper.startActivity(_activity, GuardarropasActivity.class);
            }
        }
        drawerLayout.closeDrawers();
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(drawerToggle.onOptionsItemSelected(item))
            return true;
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected int getView() {
        return R.layout.activity_main;
    }

    @Override
    public void onBackPressed() {
    }

    @Override
    public void onLoading(Boolean isLoading) {
        setProgressDialog(isLoading);
    }

    // TAB LAYOUT METHODS
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

    @Override
    public void setSpinnerItem(int index) {
    }
}
