package com.appspot.istria.histriapp.View;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.KeyEvent;

import com.appspot.istria.histriapp.Controller.DatabaseHelper;
import com.appspot.istria.histriapp.Model.InstitutionModel;
import com.appspot.istria.histriapp.R;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.splunk.mint.Mint;

import java.util.ArrayList;

public class LocationsActivity extends FragmentActivity implements OnMapReadyCallback,LocationListener  , GoogleMap.OnMarkerClickListener
{
    GoogleMap map;
    Intent intent;
    ArrayList<InstitutionModel> instituitonsArrayList = new ArrayList<>();
    DatabaseHelper databaseHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_locations);
        Mint.initAndStartSession(this.getApplication(), "0be61e22");

        intent = new Intent(this,InstitutionDescription.class);
        databaseHelper = new DatabaseHelper(this);
        instituitonsArrayList = databaseHelper.getAllInst();
        //Googles map fragment used for showing the map
        SupportMapFragment fragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        fragment.getMapAsync(this);

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        Intent intent = new Intent(LocationsActivity.this,MainSelectionAct.class);
        startActivity(intent);
        finish();
        return super.onKeyDown(keyCode, event);


    }

    //called afther getMapasync, draws the circle
    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;


        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        map.setMyLocationEnabled(true);
        Criteria criteria = new Criteria();
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        String provider = locationManager.getBestProvider(criteria, false);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }


        //CameraPosition.Builder x = CameraPosition.builder();
        //x.target(coordinate);
        //x.zoom(13);

        //Projection proj = map.getProjection();
        //Point focus = proj.toScreenLocation(coordinate);

        //map.animateCamera(CameraUpdateFactory.newLatLng(coordinate));

        for(int i=0;i<instituitonsArrayList.size();i++){
            map.animateCamera(CameraUpdateFactory.zoomBy(13));
            double lat = instituitonsArrayList.get(i).getLatitude();
            double longitude = instituitonsArrayList.get(i).getLongitude();
            String title = instituitonsArrayList.get(i).getInstitution_name();
            map.addMarker(new MarkerOptions().position(new LatLng(lat,longitude)).title(title).snippet("Working hours: 08-20"));
            CircleOptions options = new CircleOptions();
            options.center( new LatLng(lat,longitude) );
            //Radius in meters
            options.radius( 10 );
            options.fillColor( getResources()
                    .getColor( R.color.colorPrimaryDark ) );
            options.strokeColor( getResources()
                    .getColor( R.color.colorAccent ) );
            options.strokeWidth( 10 );
            map.addCircle(options);
        }


/*
        map.addMarker(new MarkerOptions().position(new LatLng(44.872929,13.849844)).title("Amfiteatar u Puli").snippet("Working hours: 08-20"));
        CircleOptions options1 = new CircleOptions();
        options1.center(new LatLng(44.872929,13.849844));
        //Radius in meters
        options1.radius( 10 );
        options1.fillColor( getResources()
                .getColor( R.color.colorPrimaryDark ) );
        options1.strokeColor( getResources()
                .getColor( R.color.colorAccent ) );
        options1.strokeWidth( 10 );
        map.addCircle(options);

        map.addMarker(new MarkerOptions().position(new LatLng(44.866623,13.849579)).title("Sveta srca").snippet("Working hours: 08-20"));
        CircleOptions options2 = new CircleOptions();
        options2.center( new LatLng(44.866623,13.849579) );
        //Radius in meters
        options2.radius( 10 );
        options2.fillColor( getResources()
                .getColor( R.color.colorPrimaryDark ) );
        options2.strokeColor( getResources()
                .getColor( R.color.colorAccent ) );
        options2.strokeWidth( 10 );
        map.addCircle(options);
        map.setOnMarkerClickListener(this);*/
    }



    @Override
    public void onLocationChanged(Location location) {

        double lat = location.getLatitude();
        double lng = location.getLongitude();

        map.addMarker(new MarkerOptions().position(new LatLng(lat,lng)).title("My"));
        CircleOptions options = new CircleOptions();
        options.center( new LatLng(lat,lng) );
                    //Radius in meters
        options.radius( 10 );
        options.fillColor( getResources()
                .getColor( R.color.colorPrimaryDark ) );
        options.strokeColor( getResources()
                .getColor( R.color.colorAccent ) );
        options.strokeWidth( 10 );
        map.addCircle(options);
    }

    @Override
    public boolean onMarkerClick(Marker marker) {

        PreferenceManager.getDefaultSharedPreferences(this).edit().putString("inst_url","http://www.ppmi.hr/hr/").apply();
        showInstitutionPopup(intent,getString(R.string.inst_location_message),marker.getTitle());

        return false;
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public  void showInstitutionPopup( final Intent intent, String message, String title){

        new AlertDialog.Builder(LocationsActivity.this)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // continue with delete
                        startActivity(intent);
                        //   ((Activity) context).finish();
                    }
                }).setNegativeButton(android.R.string.no,new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // continue with delete
                dialog.dismiss();
            }
        })
                .setIcon(getResources().getDrawable(R.drawable.ic_location_city_black_24dp))
                .show();
    }
}