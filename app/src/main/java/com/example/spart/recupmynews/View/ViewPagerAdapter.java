package com.example.spart.recupmynews.View;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.spart.recupmynews.Fragments.BusinessFragment;
import com.example.spart.recupmynews.Fragments.MostPopularFragment;
import com.example.spart.recupmynews.Fragments.TopStoriesFragment;


public class ViewPagerAdapter extends FragmentPagerAdapter {

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if (position ==0) {
            return new TopStoriesFragment();
        } else if (position == 1) {
            return new MostPopularFragment();
        } else return new BusinessFragment();
    }

    @Override
    public int getCount() {
        return 3;
    }
}