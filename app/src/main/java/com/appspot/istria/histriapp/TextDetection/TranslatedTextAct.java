package com.appspot.istria.histriapp.TextDetection;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.appspot.istria.histriapp.View.MainSelectionAct;
import com.appspot.istria.histriapp.R;

public class TranslatedTextAct extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_translated_text);
        FloatingActionButton floatingActionButton = (FloatingActionButton) findViewById(R.id.fab);
        TextView translated  = (TextView) findViewById(R.id.translated_text);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        translated.setText(sharedPreferences.getString("translatedText",""));
        floatingActionButton.setBackgroundTintList(getResources().getColorStateList(R.color.colorPrimary));

    }

    public void  go_back(View view){
        Intent intent = new Intent(this, MainSelectionAct.class);
        startActivity(intent);
        finish();
    }
}
