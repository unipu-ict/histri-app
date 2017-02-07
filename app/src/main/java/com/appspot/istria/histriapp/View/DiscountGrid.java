package com.appspot.istria.histriapp.View;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.util.DisplayMetrics;

/**
 * Created by bozidarkokot on 20/06/16.
 */
public class DiscountGrid {
    public static boolean isChecked=false;
    public static  int index = 0;
  public  static   SharedPreferences sharedPreferences;
    /**
     * Get screen width for grid layout to
     * stretch content half by half
     * */



    public static int halfScreenWidth(Context context){
        DisplayMetrics display = Resources.getSystem().getDisplayMetrics();
        return display.widthPixels/2;
    }
    /**
     * Setup grid layout from CategoryOverview
     * */
    /*
    public static void setupGridLayout(GridLayout gridLayout, final Context context) {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);

        int halfScreenWidth = halfScreenWidth(context);
        int categoryWidth = halfScreenWidth - (int)(halfScreenWidth * 0.1);
        int categoryMargin = (int)(halfScreenWidth * 0.07);
        DatabaseHelper databaseHelper = new DatabaseHelper(context);
      //  final ArrayList<Companies> categories = databaseHelper.getAllDiscount();
        gridLayout.setRowCount((int) Math.ceil(categories.size() / 2));
        gridLayout.setRowCount(4);
        gridLayout.setColumnCount(2);

        //Fill categories grid with LinearLayouts
        for(int i = 0; i < categories.size(); i++){
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
            if(i == categories.size() - 1)
                layoutParams.setMargins(categoryMargin, categoryMargin, 0, categoryMargin);
            else
                layoutParams.setMargins(categoryMargin, categoryMargin, 0, 0);
            layoutParamsSmall.setMargins(categoryMargin,categoryMargin,0,0);
            linearLayoutContainer.setLayoutParams(layoutParams);
            imageContainer.setLayoutParams(layoutParamsSmall);
            imageContainer.setLayoutParams(layoutParamsSmall);
            linearLayoutContainer.setBackgroundResource(R.drawable.main_button_round);
            linearLayoutContainer.setGravity(Gravity.CENTER);
            final TextView textView = new TextView(context);
            final ImageView iconView  = new ImageView(context);
            linearLayoutContainer.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL);
            imageContainer.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL);
            iconView.setImageResource(categoryImages[i]);

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(GridLayout.LayoutParams.WRAP_CONTENT, GridLayout.LayoutParams.MATCH_PARENT);
            params.weight = 1.0f;
            textView.setText(categories.get(i).getCompany());
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
                    sharedPreferences.edit().putString("business_name",textView.getText().toString()).apply();
                    Intent intent = new Intent(context,BusinessActivity.class);
                    context.startActivity(intent);
                    ((Activity)context).finish();


                }
            });


            iconView.setOnTouchListener(new View.OnTouchListener() {
                private Rect rect;


                @Override
                public boolean onTouch(View v, MotionEvent event) {

                  if(isChecked){
                      isChecked=false;
                      if(event.getAction() == MotionEvent.ACTION_DOWN){
                        iconView.clearColorFilter();

                      }
                      if(event.getAction() == MotionEvent.ACTION_UP){
                          iconView.clearColorFilter();

                      }
                      if(event.getAction() == MotionEvent.ACTION_MOVE){
                          iconView.clearColorFilter();

                          if(!rect.contains(v.getLeft() + (int) event.getX(), v.getTop() + (int) event.getY())){
                              iconView.clearColorFilter();
                          }
                      }
                  }else {
                     sharedPreferences.edit().putString("company",categories.get(index).getCompany()).apply();
                      isChecked=true;
                      if (event.getAction() == MotionEvent.ACTION_DOWN) {
                          iconView.setColorFilter(Color.argb(50, 0, 0, 0));
                          rect = new Rect(v.getLeft(), v.getTop(), v.getRight(), v.getBottom());
                      }
                      if (event.getAction() == MotionEvent.ACTION_UP) {
                          iconView.setColorFilter(Color.argb(0, 0, 0, 0));
                      }
                      if (event.getAction() == MotionEvent.ACTION_MOVE) {
                          if (!rect.contains(v.getLeft() + (int) event.getX(), v.getTop() + (int) event.getY())) {
                              iconView.setColorFilter(Color.argb(0, 0, 0, 0));
                          }
                      }
                  }
                    return false;
                }
            });

        }
    }*/
 }
