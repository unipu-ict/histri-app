package com.appspot.istria.histriapp.View;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.appspot.istria.histriapp.Model.InstitutionModel;
import com.appspot.istria.histriapp.Controller.DatabaseHelper;
import com.appspot.istria.histriapp.R;
import com.splunk.mint.Mint;

import java.util.ArrayList;

/**
 * Created by bozidarkokot on 20/11/16.
 */
public class InstitutionDescription extends Activity {
    Typeface croissantFont;
    DatabaseHelper databaseHelper;
    SharedPreferences sharedPreferences;
    ArrayList<InstitutionModel> arrayList=new ArrayList<>();
    WebView mWebview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_institution_description);

        Mint.initAndStartSession(this.getApplication(), "0be61e22");

        mWebview  = new WebView(this);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        mWebview.getSettings().setJavaScriptEnabled(true); // enable javascript

        final Activity activity = this;

        mWebview.setWebViewClient(new WebViewClient() {
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                Toast.makeText(activity, description, Toast.LENGTH_SHORT).show();
            }
        });

        mWebview .loadUrl(sharedPreferences.getString("inst_url",""));
        setContentView(mWebview );






    }

    public void  view_locations(View view){
        Intent intent = new Intent(InstitutionDescription.this,LocationsActivity.class);

        startActivity(intent);
        finish();
    }

    public  void  go_back(View view){
        Intent intent = new Intent(InstitutionDescription.this,MainSelectionAct.class);
        startActivity(intent);
        finish();
    }

}
