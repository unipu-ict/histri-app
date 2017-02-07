package com.appspot.istria.histriapp.View;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.GridLayout;

import com.appspot.istria.histriapp.R;
import com.splunk.mint.Mint;

public class TreasureAct extends AppCompatActivity {

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        Intent intent = new Intent(TreasureAct.this,MainSelectionAct.class);
        startActivity(intent);
        finish();
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_treasure);
      //  Mint.initAndStartSession(this.getApplication(), "0be61e22");
        GridLayout gridView = (GridLayout) findViewById(R.id.treasure_grid);
        TreasureGrid.setupGridLayout(gridView,this);
    }

}
