package com.appspot.istria.histriapp.Controller;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.appspot.istria.histriapp.AlertDialogs.CustomAlertDialog;
import com.appspot.istria.histriapp.RequestApp;
import com.appspot.istria.histriapp.SplashScreen;
import com.appspot.istria.histriapp.View.MainSelectionAct;
import com.appspot.istria.histriapp.BuildConfig;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by bozidarkokot on 20/11/16.
 */
public class FetchInstitutions {
    Bitmap bitmap;
    static DatabaseHelper db;
    static String inst_name="";
    static String inst_id ="";
    static String address = "";
    static double latitude = 0;
    static  double longitude = 0;
    static String description="";
    static String lat="";
    static String imageUrl="";
    static   InstitutionEncapsulation institutionEncapsulation;
    byte[] imgBit;
    public static boolean passedLoop = false;


    public void  fetchInstitution(final Context context, final Boolean passState){
        db = new DatabaseHelper(context);
        final StringRequest getRequest = new StringRequest(Request.Method.GET, BuildConfig.URL_ENDPOINT_ALL_INST, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                try {

                    JSONArray array = new JSONArray(response);


                    for(int i=0;i<array.length();i++){
                        JSONObject jsonObject1 = array.getJSONObject(i);
                        inst_name = jsonObject1.getString("name");
                        inst_id = jsonObject1.getString("identifier");
                        longitude = jsonObject1.getDouble("longitude");
                        latitude = jsonObject1.getDouble("latitude");
                        address = jsonObject1.getString("address");

                        db.addInstitutions(inst_id,inst_name,latitude,longitude,address,0);

                        if(i==array.length()-1 && passState ){
                            PreferenceManager.getDefaultSharedPreferences(context).edit().putInt("seenInstructions",1).apply();

                            Intent intent = new Intent(context,MainSelectionAct.class);
                            context.startActivity(intent);
                             ((Activity) context).finish();
                         //   passedLoop = true;
                        }

                       // institutionEncapsulation = new InstitutionEncapsulation(imageUrl,context,lat,longitude,description,inst_name);
                        //institutionEncapsulation.execute(imageUrl);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                  //  context.startActivity(intent);
                   // ((Activity) context).finish();
                    Intent intent = new Intent(context, SplashScreen.class);
                    CustomAlertDialog.showErrorAlert(context,intent,"You need internet access in order to use this application");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("server resp: ", "" + error);
              //  context.startActivity(intent);
               // ((Activity) context).finish();
                Intent intent = new Intent(context, SplashScreen.class);

                CustomAlertDialog.showErrorAlert(context,intent,"You will need internet access in order to use this application");

            }
        });
        RequestApp requestApp = new RequestApp();
        requestApp.setContext(context);
        requestApp.getInstance();
        requestApp.addToReqQueue(getRequest);

        // }
    }



private class  InstitutionEncapsulation extends AsyncTask<String, Void, String> {
    String url = "";
    String latit;
    Intent intent;
    String longtit;
    String desc;
    String name;
    Context context;

    private InstitutionEncapsulation(String url,Context context,String  lat,String longtitude,String desc,String name){
        this.url = url;
        this.context = context;
        this.latit = lat;
        this.longtit = longtitude;
        this.name = name;
        this.desc = desc;
    }



    private Bitmap downloadImage(String url) throws MalformedURLException {
        return  downloadImage(new URL(url));
    }


    @Override
    protected String doInBackground(String... params) {


        Log.d("loggg",url);
        Log.d("desc",desc);

        //Transform image url from response to bytes
        imgBit =  getBytes(url);
        return null;
        //  }
    }

    public Bitmap getBitmapFromURL(String src) {
        try {
            java.net.URL url = new java.net.URL(src);
            HttpURLConnection connection = (HttpURLConnection) url
                    .openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            input.close();
            return myBitmap;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    public byte[] getBytes(String url) {
        Bitmap bitmap = null;
        // try {
        bitmap = getBitmapFromURL(url);

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 80, stream);
        return stream.toByteArray();
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        //    imgBit = getBytes(url);


       // db.addInstitutions(name,description,imgBit,lat,latit);
       // ArrayList<Instituitons> arrayList = db.getAllInst();

        if(passedLoop) {
            Intent intent = new Intent(context,MainSelectionAct.class);
            context.startActivity(intent);
            ((Activity) context).finish();
        }
    }


    public  Bitmap getBitmap(){
        return bitmap;
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
            CustomAlertDialog.showErrorAlert(context,intent,"There was an error pleas try again later");
            Log.d("errrr",e.toString());
            return null;
        }


        return bmImg;
    }
}
}





