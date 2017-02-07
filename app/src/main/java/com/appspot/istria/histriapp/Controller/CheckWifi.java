package com.appspot.istria.histriapp.Controller;

import android.content.Context;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings;

/**
 * Created by bozidarkokot on 23/06/16.
 */
public class CheckWifi {

    public boolean isGPSEnabled(Context context) {

        LocationManager locationMgr = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);

        boolean GPS_Sts = locationMgr.isProviderEnabled(LocationManager.NETWORK_PROVIDER)|| locationMgr.isProviderEnabled(LocationManager.GPS_PROVIDER);

        return GPS_Sts;
    }

    public boolean getGPSStatus(Context context)
    {
        String allowedLocationProviders =
                Settings.System.getString(context.getContentResolver(),
                        Settings.System.LOCATION_PROVIDERS_ALLOWED);

        if (allowedLocationProviders == null) {
            allowedLocationProviders = "";
        }

        return allowedLocationProviders.contains(LocationManager.GPS_PROVIDER);
    }

    public boolean checkGPSState(Context context){
        final LocationManager locationmanager = (LocationManager) context.getSystemService( Context.LOCATION_SERVICE );



        if (locationmanager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            // Do whatever
            return true;
        }

        return false;
    }

    public boolean checkWifiState(Context context) {
        ConnectivityManager cm = (ConnectivityManager)context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        ConnectivityManager connManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mWifi = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mData = connManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        NetworkInfo mDatadun = connManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE_DUN);

        if(mData.isConnected()){
            return  true;
        }else
        if(mWifi.isConnected()){
      //  if (mWifi.isConnected()||mData.isAvailable()||mDatadun.isAvailable()) {
            // Do whatever
            return true;
        }

        return false;
    }
        }