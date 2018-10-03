package com.team.isc.view.adapter;

import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;


import java.util.List;

/**
 * Created by ZX on 2018/10/1.
 */

public class MainFragmentStatePagerAdapter extends FragmentStatePagerAdapter {

    List<Fragment> fragments;
    public MainFragmentStatePagerAdapter(FragmentManager fm, List<Fragment> fragmentList) {
        super(fm);
        this.fragments=fragmentList;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }
}
