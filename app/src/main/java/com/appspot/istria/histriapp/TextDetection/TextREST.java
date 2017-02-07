package com.appspot.istria.histriapp.TextDetection;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.appspot.istria.histriapp.AlertDialogs.CustomAlertDialog;
import com.appspot.istria.histriapp.RequestApp;
import com.appspot.istria.histriapp.View.MainSelectionAct;
import com.appspot.istria.histriapp.Controller.DatabaseHelper;
import com.appspot.istria.histriapp.BuildConfig;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by bozidarkokot on 12/12/16.
 */
public class TextREST {

    public  static  String translated = "";
    public  static  void chechTranslate(final Context context, String text){

        final SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        final DatabaseHelper db = new DatabaseHelper(context);
        final Intent intent = new Intent(context, TranslatedTextAct.class);

        JSONObject jsonBodyObj = new JSONObject();

        try{
            jsonBodyObj.put("translationText", text);
            jsonBodyObj.put("language", "en");

        }catch (JSONException e){
            Intent intent1 = new Intent(context, MainSelectionAct.class);
            CustomAlertDialog.showErrorAlert(context,intent1,"There was an error check internet connection and try again");
            e.printStackTrace();
        }
        final String requestBody = jsonBodyObj.toString();

        JsonObjectRequest JOPR = new JsonObjectRequest(Request.Method.POST,
                BuildConfig.URL_TRANSLATE, null, new Response.Listener<JSONObject>(){
            @Override
            public void onResponse(JSONObject response) {
                try {


                    translated = response.getString("translatedText");
                    sharedPreferences.edit().putString("translatedText",translated).apply();
                    context.startActivity(intent);
                    ((Activity) context).finish();
                    // populateLessonDetails(myActiveLessonURLFiltered);
                } catch (JSONException e) {
                    Intent intent1 = new Intent(context,MainSelectionAct.class);
                    CustomAlertDialog.showErrorAlert(context,intent1,"There was an error,check your connectivity and try again");
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.e("Error: ", error.getMessage());
                Intent intent1 = new Intent(context,MainSelectionAct.class);
                CustomAlertDialog.showErrorAlert(context,intent1,"There was an error,check your connectivity and try again");
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
}
