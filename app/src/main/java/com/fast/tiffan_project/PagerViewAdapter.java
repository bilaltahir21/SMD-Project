package com.fast.tiffan_project;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class PagerViewAdapter extends FragmentStatePagerAdapter {

    public PagerViewAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }


    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position){
            case 0:
                fragment = new fragment_menu();
                break;
            case 1:
                fragment = new fragment_order();
                break;
            case 2:
                fragment = new fragment_profile();
                break;
            case 3:
                fragment = new fragment_cart();
                break;
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return 4;
    }
}