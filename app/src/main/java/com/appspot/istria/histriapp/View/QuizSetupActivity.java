package com.appspot.istria.histriapp.View;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;

import android.os.Bundle;

import android.preference.PreferenceManager;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.widget.Toast;

import com.appspot.istria.histriapp.Locations.LocationService;
import com.appspot.istria.histriapp.Controller.FetchTreasureLocations;
import com.appspot.istria.histriapp.R;
import com.google.android.gms.maps.GoogleMap;
import com.splunk.mint.Mint;

public class QuizSetupActivity extends Activity  {
    GoogleMap map;
    private int locationFetchCounter = 0;
    final int maxLocationFetch = 2;

    private final int locationRequestCode = 1;
    public static final String LBM_EVENT_LOCATION_UPDATE = "lbmLocationUpdate";
    public static final String INTENT_FILTER_LOCATION_UPDATE = "intentFilterLocationUpdate";


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        LocationService.getInstance(this).stopLocationUpdates();
        // unregister observer
        LocalBroadcastManager.getInstance(QuizSetupActivity.this).unregisterReceiver(mLocationUpdated);
        Intent intent = new Intent(QuizSetupActivity.this,MainSelectionAct.class);
        startActivity(intent);
        finish();
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onPause() {
        super.onPause();
        // stop location updates
        LocationService.getInstance(this).stopLocationUpdates();
        // unregister observer
        LocalBroadcastManager.getInstance(QuizSetupActivity.this).unregisterReceiver(mLocationUpdated);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_setup);
       // Mint.initAndStartSession(this.getApplication(), "0be61e22");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //LocationService.getInstance(this).connectApi();
        Toast.makeText(this,"Setting up adventure environment,please wait...",Toast.LENGTH_SHORT).show();
        PreferenceManager.getDefaultSharedPreferences(this).edit().putString("inst_name","bozoadmin1").apply();
        FetchTreasureLocations.syncAdventureAndProceed(this,"bozoadmin1");


    }


    public void locationFetchFailed() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                AlertDialog.Builder builder = new AlertDialog.Builder(QuizSetupActivity.this);
                builder.setTitle(R.string.set_location_failed);
                builder.setMessage(R.string.set_location_failed_description);

                builder.setPositiveButton(R.string.location_input_continue, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(QuizSetupActivity.this,QuizSetupActivity.class);
                        startActivity(intent);
                        finish();
                    }

                });
                builder.setNegativeButton(R.string.location_prompt_cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(QuizSetupActivity.this,MainSelectionAct.class);
                        startActivity(intent);
                        finish();
                    }
                });

                builder.show();
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        LocalBroadcastManager.getInstance(QuizSetupActivity.this).registerReceiver(mLocationUpdated,
                new IntentFilter(INTENT_FILTER_LOCATION_UPDATE));
        LocationService.getInstance(this).startLocationUpdates();

    }

    private BroadcastReceiver mLocationUpdated = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            if (locationFetchCounter++ < maxLocationFetch) {
                LocationService.onBroadcastLocationUpdated(QuizSetupActivity.this, intent, mLocationUpdated);
                if (locationFetchCounter == maxLocationFetch) {
                    locationFetchFailed();
                }
            } else {

            }
                LocationService.getInstance(QuizSetupActivity.this).stopLocationUpdates();
                LocalBroadcastManager.getInstance(QuizSetupActivity.this).unregisterReceiver(mLocationUpdated);

                     }
                 };


}
