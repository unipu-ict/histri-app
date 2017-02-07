package com.appspot.istria.histriapp.Instructions;

import android.content.Context;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;

import com.appspot.istria.histriapp.R;
/**
 * Created by bozidarkokot on 18/11/16.
 */
public class InstructionsPagerAdapter extends PagerAdapter {


    public int getCount() {
        return 2;
    }

    public Object instantiateItem(View collection, int position) {

        LayoutInflater inflater = (LayoutInflater) collection.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        int resId = 0;
        switch (position) {
            case 0:
                resId = R.layout.instructions_page_first;
                break;
            case 1:
                resId = R.layout.instructions_page_second;
                break;

        }

        View view = inflater.inflate(resId, null);

        ((ViewPager) collection).addView(view, 0);




        return view;
    }

    @Override
    public void destroyItem(View arg0, int arg1, Object arg2) {
        ((ViewPager) arg0).removeView((View) arg2);

    }


    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == arg1;

    }

    @Override
    public Parcelable saveState() {
        return null;
    }
}