package com.jfq.xl_stef.jfq_android_store.fragments;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

public class TabFragmentPagerAdapter extends FragmentPagerAdapter {
    private FragmentManager fragmentManager;
    private List<Fragment> mlist;
    public TabFragmentPagerAdapter(FragmentManager fm,List<Fragment> mlist) {
        super(fm);
        this.fragmentManager=fm;
        this.mlist = mlist;
    }

    @Override
    public Fragment getItem(int position) {
        return mlist.get(position);
    }

    @Override
    public int getCount() {
        return mlist.size();
    }
}
