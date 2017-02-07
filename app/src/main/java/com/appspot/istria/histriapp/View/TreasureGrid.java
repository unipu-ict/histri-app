package com.appspot.istria.histriapp.View;

/**
 * Created by bozidarkokot on 18/01/17.
 */


import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Build;
import android.preference.PreferenceManager;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.appspot.istria.histriapp.Model.InstitutionModel;
import com.appspot.istria.histriapp.Controller.DatabaseHelper;
import com.appspot.istria.histriapp.R;

import java.util.ArrayList;

public  class TreasureGrid {


    public static int index = 0;
    public static SharedPreferences sharedPreferences;
    /**
     * Get screen width for grid layout to
     * stretch content half by half
     */


    public static ArrayList<InstitutionModel> institutionModels = new ArrayList<>();

    public static int halfScreenWidth(Context context) {
        DatabaseHelper databaseHelper = new DatabaseHelper(context);
        institutionModels = databaseHelper.getAllInst();
        DisplayMetrics display = Resources.getSystem().getDisplayMetrics();
        return display.widthPixels / 2;
    }

    /**
     * Setup grid layout from CategoryOverview
     */
    public static void setupGridLayout(GridLayout gridLayout, final Context context) {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);

        int halfScreenWidth = halfScreenWidth(context);
        int categoryWidth = halfScreenWidth - (int) (halfScreenWidth * 0.1);
        int categoryMargin = (int) (halfScreenWidth * 0.07);
        gridLayout.setRowCount((int) Math.ceil(institutionModels.size() / 2));
        gridLayout.setRowCount(4);
        gridLayout.setColumnCount(2);

        //Fill categories grid with LinearLayouts
        for (int i = 0; i < institutionModels.size(); i++) {
            index = i;
            final LinearLayout linearLayoutContainer = new LinearLayout(context);
            LinearLayout imageContainer = new LinearLayout(context);
            linearLayoutContainer.setOrientation(LinearLayout.VERTICAL);
            imageContainer.setOrientation(LinearLayout.VERTICAL);

            GridLayout.LayoutParams layoutParams = new GridLayout.LayoutParams(GridLayout.spec(GridLayout.UNDEFINED), (i % 2 == 0) ? GridLayout.spec(0) : GridLayout.spec(1));
            layoutParams.width = categoryWidth;
            layoutParams.height = categoryWidth;

            GridLayout.LayoutParams layoutParamsSmall = new GridLayout.LayoutParams(GridLayout.spec(GridLayout.UNDEFINED), (i % 2 == 0) ? GridLayout.spec(0) : GridLayout.spec(1));
            layoutParamsSmall.width = categoryWidth;
            layoutParamsSmall.height = (int) (categoryWidth * 0.8);
            if (i == institutionModels.size() - 1)
                layoutParams.setMargins(categoryMargin, categoryMargin, 0, categoryMargin);
            else
                layoutParams.setMargins(categoryMargin, categoryMargin, 0, 0);
            layoutParamsSmall.setMargins(categoryMargin, categoryMargin, 0, 0);
            linearLayoutContainer.setLayoutParams(layoutParams);
            imageContainer.setLayoutParams(layoutParamsSmall);
            imageContainer.setLayoutParams(layoutParamsSmall);
            linearLayoutContainer.setBackgroundColor(context.getResources().getColor(R.color.colorButtonSelection));
            linearLayoutContainer.setGravity(Gravity.CENTER);
            final TextView textView = new TextView(context);
            final ImageView iconView = new ImageView(context);
            linearLayoutContainer.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL);
            imageContainer.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL);

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(GridLayout.LayoutParams.FILL_PARENT, GridLayout.LayoutParams.MATCH_PARENT);
            params.weight = 1.0f;

            if(institutionModels.get(i).getPassedState()==1) {


                iconView.setImageResource(R.drawable.succes_coin);

                textView.setText(institutionModels.get(i).getInstitution_name());
                textView.setGravity(Gravity.CENTER);
                textView.setTextColor(context.getResources().getColor(R.color.black));
            }else {
                iconView.setImageResource(R.drawable.question_mark_min);


                textView.setText("Locked");
                textView.setGravity(Gravity.CENTER);
                textView.setTextColor(context.getResources().getColor(R.color.black));
            }
            imageContainer.addView(iconView);
            linearLayoutContainer.addView(imageContainer);
            linearLayoutContainer.addView(textView);
            textView.setLayoutParams(params);
            linearLayoutContainer.setPadding(0, 0, 0, categoryMargin);
            gridLayout.addView(linearLayoutContainer, layoutParams);

            final String institution_url = institutionModels.get(i).getAddress();
            linearLayoutContainer.setClickable(true);

            final int finalI = i;
            linearLayoutContainer.setOnClickListener(new View.OnClickListener() {
                @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                @Override
                public void onClick(View v) {

                //    sharedPreferences.edit().putString("inst_url", institution_url).apply();
                    if(institutionModels.get(finalI).getPassedState()==1) {

                        Intent intent = new Intent(context, RewardAvt.class);
                        context.startActivity(intent);
                        ((Activity) context).finish();
                    }

                }
            });


        }
    }
}