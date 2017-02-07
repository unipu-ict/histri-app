package com.biomech.histritreasure;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by bozidarkokot on 01/11/16.
 */
public class HuntPagerAdapter  extends FragmentPagerAdapter {

    public HuntPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment =null;
        switch (position) {
            case 0:
             //   fragment = new LocationFragment();
                break;
            case 1:
             //   fragment = new LocationFragment();
                break;
            case 2:
              //  fragment = new LocationFragment();
                break;          }
        return fragment;
    }
    @Override
    public int getCount() {
        // Show 3 total pages.
        return 3;
    }
}