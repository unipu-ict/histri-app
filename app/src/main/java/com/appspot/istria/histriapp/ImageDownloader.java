package com.appspot.istria.histriapp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by bozidarkokot on 10/11/16.
 */
public class ImageDownloader extends AsyncTask<String, Void, String>{

    public static Bitmap bitImage;
    String url="";
    Bitmap bitmap;







        public byte[] getBytes(String url) {
            Bitmap bitmap = null;
            try {
                bitmap = downloadImage(url);
            } catch (MalformedURLException e) {
                Log.d("ss",e.toString());
                e.printStackTrace();
            }
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 0, stream);
            return stream.toByteArray();
        }

        private   Bitmap downloadImage(String url) throws MalformedURLException {
          return  bitmap;
        }


        @Override
        protected String doInBackground(String... params) {

            try {
           bitmap =     downloadImage(new URL(url));
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }

        public  Bitmap getBitmap(){
            return  bitmap;
        }

        private  Bitmap downloadImage(URL url){
            Bitmap bmImg = null;
            try {
                HttpURLConnection conn= (HttpURLConnection)url.openConnection();
                conn.setDoInput(true);
                conn.connect();
                InputStream is = conn.getInputStream();

                bmImg = BitmapFactory.decodeStream(is);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                Log.d("errrr",e.toString());
                return null;
            }


            return bmImg;
        }
    }


