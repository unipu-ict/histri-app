package com.appspot.istria.histriapp.View;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.GridLayout;

import com.appspot.istria.histriapp.Controller.DatabaseHelper;
import com.appspot.istria.histriapp.Controller.FetchInstitutions;
import com.appspot.istria.histriapp.HistriApp;
import com.appspot.istria.histriapp.Model.AdventureModel;
import com.appspot.istria.histriapp.Model.InstitutionModel;
import com.appspot.istria.histriapp.R;
import com.splunk.mint.Mint;

import java.util.ArrayList;

public class MainSelectionAct extends AppCompatActivity {
    ArrayList<AdventureModel> adventureModels = new ArrayList<>();
    ArrayList<InstitutionModel> institutionModelArrayList = new ArrayList<>();
    FetchInstitutions fetchInstitutions;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_selection);

        Mint.setApplicationEnvironment(Mint.appEnvironmentTesting);
        //Mint.initAndStartSession(this.getApplication(), "0be61e22");
        Mint.initAndStartSession(this.getApplication(), "81b7dc25");
        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        adventureModels = databaseHelper.getAllAdventures();
        fetchInstitutions = new FetchInstitutions();
        institutionModelArrayList = databaseHelper.getAllInst();
        GridLayout gridView = (GridLayout) findViewById(R.id.selection_grid);
        SelectionGrid.setupGridLayout(gridView,this);

        if(institutionModelArrayList.size()==0){
            fetchInstitutions.fetchInstitution(this,false);
        }


    }
}
