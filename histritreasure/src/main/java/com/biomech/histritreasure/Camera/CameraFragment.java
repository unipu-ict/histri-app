package com.biomech.histritreasure.Camera;

import android.app.Fragment;
import android.hardware.Camera;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.biomech.histritreasure.R;

/**
 * Created by bozidarkokot on 01/11/16.
 */
public class CameraFragment extends  Fragment {

    private CameraExtraction mCameraExtraction;
    Camera mCamera;
    int mNumberOfCameras;
    int cameraId;
    int rotation;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mCameraExtraction = new CameraExtraction(
                this.getActivity().getBaseContext(),
                this.getActivity().getWindowManager().getDefaultDisplay().getRotation()
        );

        // Find the total number of cameras available
        mNumberOfCameras = Camera.getNumberOfCameras();

        // Find the ID of the rear-facing ("default") camera
        Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
        for (int i = 0; i < mNumberOfCameras; i++) {
            Camera.getCameraInfo(i, cameraInfo);
            if (cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
                cameraId = i;
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)               {
        return mCameraExtraction;
    }

    @Override
    public void onResume() {
        super.onResume();

        try {

            mCamera = Camera.open();

            mCamera.lock();
            mCameraExtraction.setCamera(mCamera);


        } catch (Exception e) {
            Log.e("cam", "failed to open Camera");
            e.printStackTrace();
        }
    }

    @Override
    public void onPause() {
        super.onPause();

        if (mCamera != null)
        {
            mCamera.release();
        }
    }

    public  void  camera_frag(View view){

    }

    // Modo en el que se pinta la cámara: encajada por dentro o saliendo los bordes por fuera.
    public enum CameraViewMode {

        /**
         * Inner mode
         */
        Inner,
        /**
         * Outer mode
         */
        Outer
    }
}