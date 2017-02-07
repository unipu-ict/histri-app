package com.appspot.istria.histriapp.View;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.appspot.istria.histriapp.Controller.DatabaseHelper;
import com.appspot.istria.histriapp.Model.Companies;
import com.appspot.istria.histriapp.R;
import com.splunk.mint.Mint;

import java.util.ArrayList;

public class BusinessActivity extends AppCompatActivity {
    DatabaseHelper databaseHelper;
    Typeface croissantFont;
    TextView companyName,description,validationDate;
    ImageView imageView;
    ArrayList<Companies> companiesArrayList = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.activity_business);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

       // TextView location_text = (TextView) findViewById(R.id.locations_text);

       // location_text.setVisibility(View.GONE);
       // Mint.initAndStartSession(this.getApplication(), "0be61e22");



       // imageView = (ImageView) findViewById(R.id.main_image_id);
        databaseHelper = new DatabaseHelper(this);
        croissantFont = Typeface.createFromAsset(getAssets(),"fonts/croissant.ttf");

        companyName.setTypeface(croissantFont);
        description.setTypeface(croissantFont);
        validationDate.setTypeface(croissantFont);

       // companiesArrayList = databaseHelper.getAllDiscount();

       // imageView.setImageResource(R.drawable.tivoli);
       // companyName.setText(companiesArrayList.get(0).getCompany());
        //description.setText(companiesArrayList.get(0).getDiscount());
       // imageView.setImageBitmap(getImage(companiesArrayList.get(0).getLogo()));





    }

    public  void  go_back(View view){
        Intent intent = new Intent(BusinessActivity.this,MainSelectionAct.class);
        startActivity(intent);
        finish();
    }

    public static Bitmap getImage(byte[] image) {
        return BitmapFactory.decodeByteArray(image, 0, image.length);
    }
}
