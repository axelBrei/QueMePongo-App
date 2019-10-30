package dds.frba.utn.quemepongo.Adapters;


import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class ViewPagerAdapter extends FragmentStatePagerAdapter {
    private List<Fragment> fragmentList;

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
        fragmentList = new ArrayList<>();
    }

    public ViewPagerAdapter(FragmentManager fm, List<Fragment> fragmentList) {
        super(fm);
        this.fragmentList = fragmentList;
    }
    public ViewPagerAdapter(FragmentManager fm, Fragment ...fragments) {
        super(fm);
        this.fragmentList = Arrays.asList(fragments);
    }

    @Override
    public Fragment getItem(int i) {
        return fragmentList.get(i);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return getItemTitle(position);
    }

    public void addFragment(Fragment f){
        fragmentList.add(f);
        notifyDataSetChanged();
    }

    public void addFragment(Fragment...f){
        fragmentList.addAll(Arrays.asList(f));
        notifyDataSetChanged();
    }

    public void setTabsTitles(TabLayout tabLayout){
        for (int i = 0; i < getCount(); i++){
            tabLayout.addTab(tabLayout.newTab().setText(getPageTitle(i)));
        }
    }

    public abstract String getItemTitle(int position);
}
