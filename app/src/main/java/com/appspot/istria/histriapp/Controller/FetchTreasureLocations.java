package com.appspot.istria.histriapp.Controller;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.appspot.istria.histriapp.BuildConfig;
import com.appspot.istria.histriapp.View.MainSelectionAct;
import com.appspot.istria.histriapp.AlertDialogs.CustomAlertDialog;
import com.appspot.istria.histriapp.RequestApp;
import com.appspot.istria.histriapp.View.QuizActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by bozidarkokot on 11/11/16.
 */
public class FetchTreasureLocations {
  public   String lat="";

    DatabaseHelper db;
  public static String riddle = "";
   public static String detections = "";
    TreasureLocationsEncapsulation imageDownloader;
   public  String longitude ="";
    public  String description = "";
    public  String imageUrl="";
    public byte[] image;
    Bitmap bitmap;
    byte[] imgBit;
    public  boolean passedLoop = false;


    public  static  void syncAdventureAndProceed(final Context context,String user_name){
        final DatabaseHelper   db = new DatabaseHelper(context);
        final Intent intent = new Intent(context,QuizActivity.class);

        JSONObject jsonBodyObj = new JSONObject();

        try{
            jsonBodyObj.put("user_name", user_name);

        }catch (JSONException e){
         //   Intent intent1 = new Intent(context, MainSelectionAct.class);
          //  CustomAlertDialog.showErrorAlert(context,intent1,"There was an error,check your internet connection and try again later");
            e.printStackTrace();
        }
        final String requestBody = jsonBodyObj.toString();

        JsonArrayRequest JOPR = new JsonArrayRequest(Request.Method.POST,
                BuildConfig.URL_ENDPOINT_ALL_CLUES, null, new Response.Listener<JSONArray>(){
            @Override
            public void onResponse(JSONArray response) {
                try {

                    String dropCategoriesSQL = "DROP TABLE IF EXISTS "+ DatabaseHelper.databaseTable.ADVENTURES.table;
                    db.getWritableDatabase().execSQL(dropCategoriesSQL);
                    String createAdventureTable = "CREATE TABLE "+ DatabaseHelper.databaseTable.ADVENTURES.table+
                            "("+ DatabaseHelper.adventureColumn.ID.column+" INTEGER PRIMARY KEY AUTOINCREMENT, "+
                            DatabaseHelper.adventureColumn.RIDDLE.column+" VARCHAR, "+
                            DatabaseHelper.adventureColumn.DESC.column+" VARCHAR);";
                    db.getWritableDatabase().execSQL(createAdventureTable);

                    VolleyLog.v("Response:%n %s", response.toString(4));

                    if(response.length()==0){
                        Intent intent1 = new Intent(context,MainSelectionAct.class);
                        CustomAlertDialog.showErrorAlert(context,intent1,"Unfornatenantly there are no clues at this location");
                    }

                    for(int i=0;i<response.length();i++){
                        JSONObject jsonObject1 = response.getJSONObject(i);
                        riddle = jsonObject1.getString("riddle");
                        detections = jsonObject1.getString("detections");
                        db.addAdventure(riddle,detections);

                        if(i+1==response.length()){
                            context.startActivity(intent);
                             ((Activity) context).finish();
                        }
                    }
                } catch (JSONException e) {
                    Intent intent1 = new Intent(context,MainSelectionAct.class);
                    CustomAlertDialog.showErrorAlert(context,intent1,"There was an error, check your internet connectivity and try again");
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.e("Error: ", error.getMessage());
                Intent intent1 = new Intent(context,MainSelectionAct.class);
                CustomAlertDialog.showErrorAlert(context,intent1,"There was an error, check your internet connectivity and try again");
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json");
                return headers;
            }


            @Override
            public byte[] getBody() {
                try {
                    return requestBody == null ? null : requestBody.getBytes("utf-8");
                } catch (UnsupportedEncodingException uee) {
                    VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s",
                            requestBody, "utf-8");
                    return null;
                }
            }


        };

       // requestQ.add(JOPR);
        RequestApp requestApp = new RequestApp();
        requestApp.setContext(context);
        requestApp.getInstance();
        requestApp.addToReqQueue(JOPR);
    }





    private class TreasureLocationsEncapsulation extends AsyncTask<String, Void, String>  {
        String url;
        String latit;
        String longtit;
        String desc;
        Context context;

        private TreasureLocationsEncapsulation(String url,Context context,String  lat,String longtitude,String desc){
            this.url = url;
            this.context = context;
            this.latit = lat;
            this.longtit = longtitude;
            this.desc = desc;

        }



        private Bitmap downloadImage(String url) throws MalformedURLException {
            return  downloadImage(new URL(url));
        }


        @Override
        protected String doInBackground(String... params) {


            Log.d("loggg",url);
            Log.d("desc",desc);


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
            db.addTreasure(latit,longtit,desc,imgBit);

            if(passedLoop==true) {
                Intent intent = new Intent(context,QuizActivity.class);
                context.startActivity(intent);
                ((Activity) context).finish();
            }
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







}
