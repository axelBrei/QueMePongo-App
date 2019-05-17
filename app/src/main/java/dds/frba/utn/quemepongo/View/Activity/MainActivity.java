package dds.frba.utn.quemepongo.View.Activity;

import android.support.annotation.Nullable;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;


import dds.frba.utn.quemepongo.Adapters.ViewPagerAdapter;
import dds.frba.utn.quemepongo.R;
import dds.frba.utn.quemepongo.View.Fragments.AtuendosFragment;
import dds.frba.utn.quemepongo.View.Fragments.PrendasFragment;
import dds.frba.utn.quemepongo.View.QueMePongoActivity;

public class MainActivity extends QueMePongoActivity  implements PrendasFragment.EventsInterface, TabLayout.OnTabSelectedListener {
    // UI
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private LinearLayout mainContainer;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //BUSCO UI
        viewPager = findViewById(R.id.MainScreenViewPager);
        tabLayout = findViewById(R.id.MainScreenTabLayout);
        mainContainer = findViewById(R.id.MainScreenContainer);


        // CREO SPINNER EN TOOLBAR
        setToolbarSpinner(true);
        // INSTANCIO EL ADAPTER DEL VIEW PAGER
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
        // INSTANCIO EL TAB LAYOUT
        tabLayout.addOnTabSelectedListener(this);
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        adapter.setTabsTitles(tabLayout);
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(0);
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

    @Override
    public void setSpinnerItem(int index) {
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

}
