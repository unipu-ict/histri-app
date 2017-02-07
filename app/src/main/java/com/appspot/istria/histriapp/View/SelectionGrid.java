package com.appspot.istria.histriapp.View;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.os.Build;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.appspot.istria.histriapp.Controller.CheckWifi;
import com.appspot.istria.histriapp.R;
import com.appspot.istria.histriapp.TextDetection.OcrCaptureActivity;

/**
 * Created by bozidarkokot on 21/12/16.
 */
public class SelectionGrid {
    private static final int RC_OCR_CAPTURE = 9003;
    static CheckWifi checkWifi = new CheckWifi();
        public static boolean isChecked=false;
        public static  int index = 0;
        static Typeface croissantFont;
        public  static SharedPreferences sharedPreferences;
        /**
         * Get screen width for grid layout to
         * stretch content half by half
         * */

        public static int[] categoryImages = {
                // Uvijek nula, nemamo nulti index na bazi
                R.drawable.ic_play_for_work_black_24dp,
                R.drawable.ic_location_city_black_24dp,
                R.drawable.ic_loyalty_black_24dp,
                R.drawable.ic_g_translate_black_24dp,
               // R.drawable.ic_room_black_24dp
        };
    public static String[] categories = {"Start","Locations","Treasures","Translator"};

    public static int halfScreenWidth(Context context){
        DisplayMetrics display = Resources.getSystem().getDisplayMetrics();
        return display.widthPixels/2;
    }
    /**
     * Setup grid layout from CategoryOverview
     * */
    public static void setupGridLayout(GridLayout gridLayout, final Context context) {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);

        croissantFont = Typeface.createFromAsset(context.getAssets(),"fonts/croissant.ttf");
        int halfScreenWidth = halfScreenWidth(context);
        int categoryWidth = halfScreenWidth - (int)(halfScreenWidth * 0.1);
        int categoryMargin = (int)(halfScreenWidth * 0.07);
        gridLayout.setRowCount((int) Math.ceil(categories.length / 2));
        gridLayout.setRowCount(4);
        gridLayout.setColumnCount(2);

        //Fill categories grid with LinearLayouts
        for(int i = 0; i < categories.length; i++){
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
            layoutParamsSmall.height = (int)(categoryWidth * 0.8);
            if(i == categories.length - 1)
                layoutParams.setMargins(categoryMargin, categoryMargin, 0, categoryMargin);
            else
                layoutParams.setMargins(categoryMargin, categoryMargin, 0, 0);
            layoutParamsSmall.setMargins(categoryMargin,categoryMargin,0,0);
            linearLayoutContainer.setLayoutParams(layoutParams);
            imageContainer.setLayoutParams(layoutParamsSmall);
            imageContainer.setLayoutParams(layoutParamsSmall);
            linearLayoutContainer.setBackgroundColor(context.getResources().getColor(R.color.colorButtonSelection));
            linearLayoutContainer.setGravity(Gravity.CENTER);
            final TextView textView = new TextView(context);
            final ImageView iconView  = new ImageView(context);
            linearLayoutContainer.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL);
            imageContainer.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL);
            iconView.setImageResource(categoryImages[i]);

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(GridLayout.LayoutParams.FILL_PARENT, GridLayout.LayoutParams.MATCH_PARENT);
            params.weight = 1.0f;
            textView.setText(categories[i]);
            textView.setGravity(Gravity.CENTER);
            textView.setTextColor(context.getResources().getColor(R.color.black));
            imageContainer.addView(iconView);
            linearLayoutContainer.addView(imageContainer);
            linearLayoutContainer.addView(textView);
            textView.setLayoutParams(params);
            linearLayoutContainer.setPadding(0, 0, 0, categoryMargin);
            gridLayout.addView(linearLayoutContainer, layoutParams);

            linearLayoutContainer.setClickable(true);

            linearLayoutContainer.setOnClickListener(new View.OnClickListener() {
                @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                @Override
                public void onClick(View v) {

                    switch (textView.getText().toString()){
                        case "Start":
                            boolean gps = checkWifi.isGPSEnabled(context);
                            boolean wifiState = checkWifi.checkWifiState(context);
                            if (gps && wifiState) {
                                Intent intent = new Intent(context,QuizSetupActivity.class);
                                // sharedPreferences.edit().putString("inst_id",finalId).apply();
                                context.startActivity(intent);
                                ((Activity)context).finish();
                            } else {
                                new AlertDialog.Builder(context)
                                        .setTitle(context.getString(R.string.no_gps_internet))
                                        .setMessage(context.getString(R.string.no_gps_internet_alert))
                                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                // continue with delete
                                                context.startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
                                                context.startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));

                                            }
                                        })
                                        .show();
                            }
                            break;

                        case "Institutions":

                            Intent intentInstitutions = new Intent(context,InstitutionsSelectionAct.class);
                           // sharedPreferences.edit().putString("inst_id",finalId).apply();
                            context.startActivity(intentInstitutions);
                            ((Activity)context).finish();
                            break;

                        case "Translator":
                            Intent intentTranslate = new Intent(context, OcrCaptureActivity.class);
                            intentTranslate.putExtra(OcrCaptureActivity.AutoFocus, true);
                            //   intent.putExtra(OcrCaptureActivity.UseFlash, useFlash.isChecked());

                            ((Activity)context).startActivityForResult(intentTranslate, RC_OCR_CAPTURE);
                            break;
                        case "Locations":
                            Intent intentDirections = new Intent(context,LocationsActivity.class);
                            // sharedPreferences.edit().putString("inst_id",finalId).apply();
                            context.startActivity(intentDirections);
                            ((Activity)context).finish();
                            break;
                        case "Treasures":
                            
                            Intent intentTreasures = new Intent(context,TreasureAct.class);
                            // sharedPreferences.edit().putString("inst_id",finalId).apply();
                            context.startActivity(intentTreasures);
                            ((Activity)context).finish();
                          //  CustomAlertDialog.
                            break;

                        default:
                            break;
                    }


                }
            });




        }
    }
}


