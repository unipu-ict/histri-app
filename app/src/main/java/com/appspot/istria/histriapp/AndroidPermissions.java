package com.appspot.istria.histriapp;

/**
 * Created by bozidarkokot on 04/11/16.
 */

import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

/**
 * Created by bozidarkokot on 21/09/16.
 */
public class AndroidPermissions  extends Activity {
    Activity activity;
    final int ACCESS_FINE_LOCATION = 2;
    final int CAMERA = 1;
    final int ACCESS_COARSE_LOCATION = 3;
    final int WRITE_EXTERNAL_STORAGE = 5;
    final int ACCESS_NETWORK_STATE = 6;
    final int CHANGE_NETWORK_STATE = 7;
    final int ACCESS_WIFI_STATE = 8;
    final int ACTION_IMAGE_CAPTURE = 10;
    final int READ_PHONE_STATE = 11;
    final int BLUETOOTH = 12;
    final int BLUETOOTH_ADMIN = 13;
    final int INTERNET = 14;
    final int READ_EXTERNAL_STORAGE = 15;

    public AndroidPermissions(Activity ac) {
        activity = ac;
    }

    //**********************READ AND WRITE**********************************
    public void readWritePermission() {
        if (ContextCompat.checkSelfPermission(activity,
                android.Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity,
                    android.Manifest.permission.READ_EXTERNAL_STORAGE)) {
                //nothing to be inserted here
            } else {
                ActivityCompat.requestPermissions(activity,
                        new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE},
                        READ_EXTERNAL_STORAGE);
            }
        }
        if (ContextCompat.checkSelfPermission(activity,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity,
                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                //nothing to be inserted here
            } else {
                ActivityCompat.requestPermissions(activity,
                        new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        WRITE_EXTERNAL_STORAGE);
            }
        }


    }

    //**********************GPS**********************************
    // this is needed for bluetooth search also
    public void gpsPermissions() {
        if (ContextCompat.checkSelfPermission(activity,
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity,
                    android.Manifest.permission.ACCESS_FINE_LOCATION)) {
                //nothing to be inserted here
            } else {
                ActivityCompat.requestPermissions(activity,
                        new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                        ACCESS_FINE_LOCATION);
            }
        }
        if (ContextCompat.checkSelfPermission(activity,
                android.Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity,
                    android.Manifest.permission.ACCESS_COARSE_LOCATION)) {
                //nothing to be inserted here
            } else {
                ActivityCompat.requestPermissions(activity,
                        new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION},
                        ACCESS_COARSE_LOCATION);
            }
        }
    }

    //**********************CAMERA**********************************
    public void cameraAndInternetPermission() {

        if (ContextCompat.checkSelfPermission(activity,
                android.Manifest.permission.INTERNET)
                != PackageManager.PERMISSION_GRANTED) {
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity,
                    android.Manifest.permission.INTERNET)) {
                //nothing to be inserted here
            } else {
                ActivityCompat.requestPermissions(activity,
                        new String[]{android.Manifest.permission.INTERNET},
                        INTERNET);
            }
        }
        if (ContextCompat.checkSelfPermission(activity,
                android.Manifest.permission.READ_PHONE_STATE)
                != PackageManager.PERMISSION_GRANTED) {
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity,
                    android.Manifest.permission.READ_PHONE_STATE)) {
                //nothing to be inserted here
            } else {
                ActivityCompat.requestPermissions(activity,
                        new String[]{android.Manifest.permission.READ_PHONE_STATE},
                        READ_PHONE_STATE);
            }
        }


    }
    //************************PHONE STATE*****************************
    public  void  setREAD_PHONE_STATE(){
        if (ContextCompat.checkSelfPermission(activity,
                android.Manifest.permission.READ_PHONE_STATE)
                != PackageManager.PERMISSION_GRANTED) {
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity,
                    android.Manifest.permission.READ_PHONE_STATE)) {
                //nothing to be inserted here
            } else {
                ActivityCompat.requestPermissions(activity,
                        new String[]{android.Manifest.permission.READ_PHONE_STATE},
                        READ_PHONE_STATE);
            }
        }
        if (ContextCompat.checkSelfPermission(activity,
                android.Manifest.permission.READ_PHONE_STATE)
                != PackageManager.PERMISSION_GRANTED) {
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity,
                    android.Manifest.permission.READ_PHONE_STATE)) {
                //nothing to be inserted here
            } else {
                ActivityCompat.requestPermissions(activity,
                        new String[]{android.Manifest.permission.READ_PHONE_STATE},
                        READ_PHONE_STATE);
            }
        }
    }

    //**********************BLUETOOTH**********************************
    public void blueToothPermission() {
        if (ContextCompat.checkSelfPermission(activity,
                android.Manifest.permission.BLUETOOTH)
                != PackageManager.PERMISSION_GRANTED) {
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity,
                    android.Manifest.permission.BLUETOOTH)) {
                //nothing to be inserted here
            } else {
                ActivityCompat.requestPermissions(activity,
                        new String[]{android.Manifest.permission.BLUETOOTH},
                        BLUETOOTH);
            }
        }
        if (ContextCompat.checkSelfPermission(activity,
                android.Manifest.permission.BLUETOOTH_ADMIN)
                != PackageManager.PERMISSION_GRANTED) {
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity,
                    android.Manifest.permission.BLUETOOTH_ADMIN)) {
                //nothing to be inserted here
            } else {
                ActivityCompat.requestPermissions(activity,
                        new String[]{android.Manifest.permission.BLUETOOTH_ADMIN},
                        BLUETOOTH_ADMIN);
            }
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case ACCESS_FINE_LOCATION:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission Granted
                } else {
                    // Permission Denied
                }
                break;
            case CAMERA:
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                } else {

                }
                break;
            case ACCESS_COARSE_LOCATION:
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                } else {

                }
                break;
            case WRITE_EXTERNAL_STORAGE:
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                } else {

                }
                break;
            case ACCESS_NETWORK_STATE:
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                } else {

                }
                break;
            case ACCESS_WIFI_STATE:
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                } else {

                }
                break;
            case CHANGE_NETWORK_STATE:
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                } else {

                }
                break;
            case INTERNET:
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                } else {

                }
                break;
            case BLUETOOTH:
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                } else {

                }
                break;
            case BLUETOOTH_ADMIN:
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                } else {

                }
                break;
            case READ_EXTERNAL_STORAGE:
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                } else {

                }
                break;
            case READ_PHONE_STATE:
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                } else {

                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

}
