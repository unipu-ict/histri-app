package com.biomech.histritreasure;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Fragment;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.LocationSource;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Created by bozidarkokot on 01/11/16.
 */
public  class LocationFragment extends Fragment implements LocationListener{
GoogleMap map;
    MapView mMapView;


    public LocationFragment(){
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View dataView = inflater.inflate(R.layout.location_fragment,
                container, false);

        mMapView =(MapView) dataView.findViewById(R.id.mapView);

        mMapView.onCreate(savedInstanceState);

        mMapView.onResume(); // needed to get the map to display immediately

        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        mMapView.getMapAsync(new OnMapReadyCallback() {
            @TargetApi(Build.VERSION_CODES.M)
            @Override
            public void onMapReady(GoogleMap mMap) {
                map = mMap;


//                if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                  //  return;
              //  }
             //   map.setMyLocationEnabled(true);
                Criteria criteria = new Criteria();
              //  LocationManager locationManager = (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);
               // String provider = locationManager.getBestProvider(criteria, false);
               // if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
               //     return;
               // }


                //CameraPosition.Builder x = CameraPosition.builder();
                //x.target(coordinate);
                //x.zoom(13);

                //Projection proj = map.getProjection();
                //Point focus = proj.toScreenLocation(coordinate);

                //map.animateCamera(CameraUpdateFactory.newLatLng(coordinate));
                map.animateCamera(CameraUpdateFactory.zoomBy(13));

                map.addMarker(new MarkerOptions().position(new LatLng(44.870213,13.845518)).title("Povijesni i pomorski muzej"));
                CircleOptions options = new CircleOptions();
                options.center( new LatLng(44.870213,13.845518) );
                //Radius in meters
                options.radius( 100 );
                options.fillColor( getResources()
                        .getColor( R.color.colorAccent ) );
                options.strokeColor( getResources()
                        .getColor( R.color.colorAccent ) );
                //options.strokeWidth( 100 );
                map.addCircle(options);

                map.addMarker(new MarkerOptions().position(new LatLng(44.872929,13.849844)).title("Amfiteatar u Puli"));
                CircleOptions options1 = new CircleOptions();
                options1.center( new LatLng(44.872929,13.849844) );
                //Radius in meters
                options1.radius( 10 );
                options1.fillColor( getResources()
                        .getColor( R.color.colorAccent ) );
                options1.strokeColor( getResources()
                        .getColor( R.color.colorAccent ) );
                options1.strokeWidth( 10 );
                map.addCircle(options);

                map.addMarker(new MarkerOptions().position(new LatLng(44.866623,13.849579)).title("Sveta srca"));
                CircleOptions options2 = new CircleOptions();
                options2.center( new LatLng(44.866623,13.849579) );
                //Radius in meters
                options2.radius( 10 );
                options2.fillColor( getResources()
                        .getColor( R.color.colorAccent ) );
                options2.strokeColor( getResources()
                        .getColor( R.color.colorAccent ) );
                options2.strokeWidth( 10 );
                map.addCircle(options);
            }
        });


        return dataView;
    }



    //called afther getMapasync, draws the circle



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
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }

}
