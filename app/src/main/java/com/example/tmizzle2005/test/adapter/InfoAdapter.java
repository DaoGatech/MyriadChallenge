package com.example.tmizzle2005.test.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by tmizzle2005 on 3/19/15.
 */
public class InfoAdapter extends FragmentPagerAdapter{
    private List<Fragment> fragments;
    public InfoAdapter(FragmentManager fm, List<Fragment> fragments) {
        super(fm);
        this.fragments = fragments;
    }
    @Override
    public Fragment getItem(int position) {
        return this.fragments.get(position);
    }


    @Override

    public int getCount() {

        return this.fragments.size();

    }

}


