package com.appspot.istria.histriapp;

import android.app.Application;
import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by bozidarkokot on 21/06/16.
 */
public class RequestApp extends Application {

    private RequestQueue mRequestQueue;
    private static RequestApp mInstance;
    private Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
    }

    public void setContext(Context context){
        this.context = context;
    }

    public static synchronized RequestApp getInstance() {
        return mInstance;
    }

    public RequestQueue getReqQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(context.getApplicationContext());
        }

        return mRequestQueue;
    }

    public <T> void addToReqQueue(Request<T> req, String tag) {

        getReqQueue().add(req);
    }

    public <T> void addToReqQueue(Request<T> req) {

        getReqQueue().add(req);
    }

    public void cancelPendingReq(Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }
}