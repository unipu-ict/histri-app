package com.appspot.istria.histriapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.appspot.istria.histriapp.Model.InstitutionModel;
import com.appspot.istria.histriapp.View.MainSelectionAct;
import com.appspot.istria.histriapp.Controller.CheckWifi;
import com.appspot.istria.histriapp.Controller.DatabaseHelper;
import com.appspot.istria.histriapp.Controller.FetchInstitutions;
import com.appspot.istria.histriapp.R;

import com.splunk.mint.Mint;

import java.util.ArrayList;

public class SplashScreen extends AppCompatActivity {
    ArrayList<InstitutionModel> institutionModelArrayList = new ArrayList<>();
    DatabaseHelper databaseHelper;
    CheckWifi checkWifi = new CheckWifi();
    FetchInstitutions fetchInstitutions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

       // Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
       // setSupportActionBar(toolbar);
        databaseHelper = new DatabaseHelper(this);
        fetchInstitutions = new FetchInstitutions();
        institutionModelArrayList = databaseHelper.getAllInst();



    if(institutionModelArrayList.size()==0) {


       // boolean gps = checkWifi.isGPSEnabled(SplashScreen.this);
        boolean wifiState = checkWifi.checkWifiState(SplashScreen.this);
        if ( wifiState) {
            fetchInstitutions.fetchInstitution(this, true);

        } else {
            new AlertDialog.Builder(SplashScreen.this)
                    .setTitle(SplashScreen.this.getString(R.string.no_internet_splash))
                    .setMessage(SplashScreen.this.getString(R.string.no_internet_body_splash))
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // continue with delete
                          // SplashScreen.this.startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
                        finish();
                        }
                    })
                    .show();
        }

    }else {
        Intent intent = new Intent(this,MainSelectionAct.class);
        startActivity(intent);
        finish();
    }



    }

}
