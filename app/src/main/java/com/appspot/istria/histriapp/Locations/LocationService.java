package com.appspot.istria.histriapp.Locations;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Geocoder;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.appspot.istria.histriapp.Model.InstitutionModel;
import com.appspot.istria.histriapp.View.MainSelectionAct;
import com.appspot.istria.histriapp.Controller.CheckWifi;
import com.appspot.istria.histriapp.Controller.DatabaseHelper;
import com.appspot.istria.histriapp.Controller.FetchTreasureLocations;
import com.appspot.istria.histriapp.R;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by bozidarkokot on 28/10/16.
 */
public class LocationService extends Service implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        com.google.android.gms.location.LocationListener {

    static Context context;
    DatabaseHelper databaseHelper;
   static ArrayList<InstitutionModel> institutionModels = new ArrayList<>();
    static CheckWifi checkWifi;
    private static LocationService mInstance = null;
    protected GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;
    public static float[] institutionDistance = new float[3];
    // the parameters for the radius of address search
    public final static int FAST_LOCATION_FREQUENCY = 5 * 200;
    public final static int LOCATION_FREQUENCY = 5 * 200;
    // const info for broadcast
    public static final String LBM_EVENT_LOCATION_UPDATE = "lbmLocationUpdate";
    public static final String INTENT_FILTER_LOCATION_UPDATE = "intentFilterLocationUpdate";


    public LocationService(Context context) {
        this.context = context;
        buildGoogleApiClient(context);
        databaseHelper = new DatabaseHelper(context);
        institutionModels = databaseHelper.getAllInst();
        checkWifi = new CheckWifi();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        stopLocationUpdates();
    }

    public static LocationService getInstance(Context context) {
        if (mInstance == null) mInstance = new LocationService(context);
        return mInstance;
    }


    private synchronized void buildGoogleApiClient(Context context) {// setup googleapi client
        mGoogleApiClient = new GoogleApiClient.Builder(context)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API).build();
        mGoogleApiClient.connect();
        configRequestLocationUpdate();// setup location updates
    }

    private void configRequestLocationUpdate() {//config request location update
        mLocationRequest = new LocationRequest()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(LOCATION_FREQUENCY)
                .setFastestInterval(FAST_LOCATION_FREQUENCY);
    }

    private void requestLocationUpdates() {//request location updates

            Log.d("lokac","l");
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // Check Permissions Now
            ActivityCompat.requestPermissions((Activity) context,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},2
            );
        } else {
            // permission has been granted, request location
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);

        }


    }
    //called in login screen when the automatic option is checked
    public void startLocationUpdates() {// connect and force the updates

        if (mGoogleApiClient.isConnected())
            requestLocationUpdates();
    }

    public void stopLocationUpdates() {// stop updates, disconnect from google api
        if (mGoogleApiClient != null && mGoogleApiClient.isConnected()) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
            mGoogleApiClient.disconnect();
        }
    }

    public Location getLastLocation() {
        if (null != mGoogleApiClient && mGoogleApiClient.isConnected()) {
            // return last location
            if(Build.VERSION.SDK_INT>=23){
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED){
                return LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
            }
            }

        //    return LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);

        }
        startLocationUpdates(); // start the updates
        return null;
    }
    //connects the api client
    public  void  connectApi(){
        mGoogleApiClient.connect();
    }

    @Override//request location updates
    public void onConnected(Bundle bundle) {
    Log.d("connected","c");
     //   PreferenceManager.getDefaultSharedPreferences(context).edit().putString("inst_id","1").apply();;
        //FetchQuestions.fetchQuestionsAndProceed(context);
        if(!checkWifi.checkWifiState(context)){
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle(R.string.no_internet);
            builder.setMessage(R.string.no_internet_alert);
            builder.setPositiveButton(R.string.location_input_continue, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    Intent intent = new Intent(context,MainSelectionAct.class);
                    context.startActivity(intent);
                    ((Activity)context).finish();
                }

            });

            builder.show();
        }else {
            startLocationUpdates();
        }
     //   Log.d("lokk", String.valueOf(getLastLocation()));
      //  Intent intent = new Intent(INTENT_FILTER_LOCATION_UPDATE).putExtra(LBM_EVENT_LOCATION_UPDATE, getLastLocation());


    }

    @Override
    public void onConnectionSuspended(int i) {
        if (mGoogleApiClient != null) mGoogleApiClient.connect();
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.d("zastoo",connectionResult.toString());
    }

    @Override//location listener callback automaticly called when the location is fetched
    public void onLocationChanged(Location location) {
        if (location != null) {
            Log.d("lokacija",location.toString());
            Intent intent = new Intent(INTENT_FILTER_LOCATION_UPDATE).putExtra(LBM_EVENT_LOCATION_UPDATE, location);
            LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
        }
    }

    public static void onBroadcastLocationUpdated(final Context context, Intent intent, BroadcastReceiver mLocationUpdated){
        Location location = intent.getParcelableExtra(LocationService.LBM_EVENT_LOCATION_UPDATE);
        Geocoder geocoder = new Geocoder(context, Locale.getDefault());
      //  try {
           // List<Address> list = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);

            //String address = geocoder.getFromLocation(location.getLatitude(),location.getLongitude(),1).get(0).getAddressLine(0);
            //Log.d("adss","");

            findLocationAndProceed(location,context);

            LocationService.getInstance(context).stopLocationUpdates();
            LocalBroadcastManager.getInstance(context).unregisterReceiver(mLocationUpdated);
    //    } catch (IOException e) {
           // e.printStackTrace();
      //  }

    }

    public static void  findLocationAndProceed(Location location, final Context context){
        boolean locationState = false;

       // FetchTreasureLocations.syncAdventureAndProceed(context,"bozoadmin1");


        for(int i=0;i<institutionModels.size();i++){
            double distance = meterDistanceBetweenPoints(institutionModels.get(i).getLatitude(),institutionModels.get(i).getLongitude(),location.getLatitude(),location.getLongitude());
            if(distance<50){
                locationState = true;
                FetchTreasureLocations.syncAdventureAndProceed(context,institutionModels.get(i).getInstitution_identifier());
                break;
            }

        }

                if(!locationState) {
                    FetchTreasureLocations.syncAdventureAndProceed(context,"bozoadmin1");

                 /*   final Intent intent = new Intent(context,MainSelectionAct.class);
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle(R.string.location_out_of_range);
                    builder.setMessage(R.string.location_out_of_range_alert);
                    builder.setPositiveButton(R.string.location_input_continue, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            context.startActivity(intent);
                            ((Activity) context).finish();
                        }

                    });

                    builder.show();*/
                }
          //  }
        }



    public static double meterDistanceBetweenPoints(double lat_a, double lng_a, double lat_b, double lng_b) {
        float pk = (float) (180.f/Math.PI);

        double a1 = lat_a / pk;
        double a2 = lng_a / pk;
        double b1 = lat_b / pk;
        double b2 = lng_b / pk;

        double t1 = Math.cos(a1)*Math.cos(a2)*Math.cos(b1)*Math.cos(b2);
        double t2 = Math.cos(a1)*Math.sin(a2)*Math.cos(b1)*Math.sin(b2);
        double t3 = Math.sin(a1)*Math.sin(b1);
        double tt = Math.acos(t1 + t2 + t3);

        return 6366000*tt;
    }
}