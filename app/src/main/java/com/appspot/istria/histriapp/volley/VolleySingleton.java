package com.appspot.istria.histriapp.volley;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

/**
 * Created by bozidarkokot on 06/11/16.
 */
public class VolleySingleton {

        private static VolleySingleton mInstance;
        private RequestQueue mRequestQueue;
        private ImageLoader mImageLoader;
        private static Context mCtx;

        /**
         * Private constructor, only initialization from getInstance.
         *
         * @param context parent context
         */
        private VolleySingleton(Context context) {
        mCtx = context;
        mRequestQueue = getRequestQueue();


    }

        /**
         * Singleton construct design pattern.
         *
         * @param context parent context
         * @return single instance of VolleySingleton
         */
    public static synchronized VolleySingleton getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new VolleySingleton(context);
        }
        return mInstance;
    }

    /**
     * Get current request queue.
     *
     * @return RequestQueue
     */
    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            // getApplicationContext() is key, it keeps you from leaking the
            // Activity or BroadcastReceiver if someone passes one in.
            mRequestQueue = Volley.newRequestQueue(mCtx.getApplicationContext());
        }
        return mRequestQueue;
    }

    /**
     * Add new request depend on type like string, json object, json array request.
     *
     * @param req new request
     * @param <T> request type
     */
    public <T> void addToRequestQueue(Request<T> req) {
        getRequestQueue().add(req);
    }

    /**
     * Get image loader.
     *
     * @return ImageLoader
     */
    public ImageLoader getImageLoader() {
        return mImageLoader;
    }
}
