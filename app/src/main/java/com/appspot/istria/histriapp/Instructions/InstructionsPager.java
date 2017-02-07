package com.appspot.istria.histriapp.Instructions;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.appspot.istria.histriapp.AndroidPermissions;
import com.appspot.istria.histriapp.Controller.FetchInstitutions;
import com.appspot.istria.histriapp.R;

public class InstructionsPager extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructions_pager);



      //  InstructionsPagerAdapter adapter = new InstructionsPagerAdapter();
       // ViewPager myPager = (ViewPager) findViewById(R.id.instructions_pager);
       // myPager.setAdapter(adapter);
       // myPager.setCurrentItem(0);
        AndroidPermissions androidPermissions = new AndroidPermissions(this);
        androidPermissions.cameraAndInternetPermission();


    }


    public void end_instructions_click(View view){
        FetchInstitutions fetchInstitutions = new FetchInstitutions();
       fetchInstitutions.fetchInstitution(this,true);
      //  Intent intent = new Intent(this, MainSelectionAct.class);
       // startActivity(intent);
       // finish();
    }


}
