package com.biomech.histritreasure.Pictures;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.biomech.histritreasure.R;

/**
 * Created by bozidarkokot on 01/11/16.
 */
public class PictureFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View dataView = inflater.inflate(R.layout.picture_fragment,
                container, false);
        return dataView;
    }
    }
