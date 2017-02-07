package com.appspot.istria.histriapp;

import android.content.Context;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;

import com.splunk.mint.Mint;

/**
 * Created by bozidarkokot on 28/10/16.
 */
public class HistriApp extends MultiDexApplication {
    private static Context mContextApplication;

    public void onCreate(){
        super.onCreate();

        mContextApplication = getApplicationContext();// set app context
    }
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    public static Context getAppContext(){//retrieve application context
        return mContextApplication;
    }
}