package com.appspot.istria.histriapp.View;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.GridLayout;

import com.appspot.istria.histriapp.R;
import com.splunk.mint.Mint;

/**
 * Created by bozidarkokot on 20/11/16.
 */
public class InstitutionsSelectionAct extends Activity {

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        Intent intent = new Intent(InstitutionsSelectionAct.this,MainSelectionAct.class);
        startActivity(intent);
        finish();
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Mint.initAndStartSession(this.getApplication(), "0be61e22");

        setContentView(R.layout.activity_institution_selection);
        GridLayout gridView = (GridLayout) findViewById(R.id.institution_grid);
        InstitutionGrid.setupGridLayout(gridView,this);


    }
}
