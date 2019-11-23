package com.fast.tiffan_project;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class PagerViewAdapter extends FragmentStatePagerAdapter {


    private final Fragment fragmentMenu = new fragment_menu();
    private final Fragment fragmentCart = new fragment_cart();
    private final Fragment fragmentOrder = new fragment_order();
    private final Fragment fragmentProfile = new fragment_profile();

    public PagerViewAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position){
            case 0:
                fragment = fragmentMenu;
                break;
            case 1:
                fragment = fragmentOrder;
                break;
            case 2:
                fragment = fragmentProfile;
                break;
            case 3:
                fragment = fragmentCart;
                break;
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return 4;
    }
}