package com.subhamgupta.androidtask.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class ViewPagerAdapter extends FragmentPagerAdapter {
    private final List<Fragment> fragments = new ArrayList<>();
    private List<String> fragmentstitle = new ArrayList<>();
    public ViewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
        //Log.e("frag",fm.toString());
    }
    public void addFragments(Fragment fragment, String title){
        fragments.add(fragment);
        fragmentstitle.add(title);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        //Log.e("getitem", String.valueOf(position));
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }
    public CharSequence getPageTitle(int position) {
        //Log.e("getpagetitle",fragmentstitle.get(position));
        return fragmentstitle.get(position);
    }
}
