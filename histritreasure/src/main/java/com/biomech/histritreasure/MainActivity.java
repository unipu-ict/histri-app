package com.biomech.histritreasure;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.biomech.histritreasure.Camera.CameraFragment;
import com.biomech.histritreasure.Pictures.PictureFragment;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity{
    FragmentPagerAdapter adapterViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AndroidPermissions androidPermissions = new AndroidPermissions(this);
        androidPermissions.cameraAndInternetPermission();
        Fragment fragment = Fragment.instantiate(this, LocationFragment.class.getName());
        FragmentTransaction ft = getFragmentManager().beginTransaction();

        ft.replace(R.id.mapView, fragment);
        ft.commit();
    }

    public void location_frag(View view){
        Fragment fragment = Fragment.instantiate(this, LocationFragment.class.getName());
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.mapView, fragment);
        ft.commit();
    }
    public  void  camera_frag(View view){


        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, 1);
        }
      //  Fragment fragment = Fragment.instantiate(this, CameraFragment.class.getName());
      //  FragmentTransaction ft = getFragmentManager().beginTransaction();
      //  ft.replace(R.id.mapView, fragment);
       // ft.commit();
    }
    public  void  targeted_picture(View view){
        Fragment fragment = Fragment.instantiate(this, PictureFragment.class.getName());
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.mapView, fragment);
        ft.commit();
    }


    public static Intent openImageIntent(Context context, Uri cameraOutputFile) {

        // Camera.
        final List<Intent> cameraIntents = new ArrayList<Intent>();
        final Intent captureIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        final PackageManager packageManager = context.getPackageManager();
        final List<ResolveInfo> listCam = packageManager.queryIntentActivities(captureIntent, 0);
        for(ResolveInfo res : listCam) {
            final String packageName = res.activityInfo.packageName;
            final Intent intent = new Intent(captureIntent);
            intent.setComponent(new ComponentName(res.activityInfo.packageName, res.activityInfo.name));
            intent.setPackage(packageName);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, cameraOutputFile);
            cameraIntents.add(intent);
        }

        // Filesystem.
        final Intent galleryIntent = new Intent();
        galleryIntent.setType("image/*");
        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);

        // Chooser of filesystem options.
        final Intent chooserIntent = Intent.createChooser(galleryIntent, "Take or select pic");

        // Add the camera options.
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, cameraIntents.toArray(new Parcelable[]{}));
        return chooserIntent;
    }

}
