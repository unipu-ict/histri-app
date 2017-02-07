package com.appspot.istria.histriapp.View;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.media.AudioManager;
import android.media.ExifInterface;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.appspot.istria.histriapp.Model.AdventureModel;
import com.appspot.istria.histriapp.BuildConfig;
import com.appspot.istria.histriapp.Controller.DatabaseHelper;
import com.appspot.istria.histriapp.PermissionUtilities;
import com.appspot.istria.histriapp.R;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.vision.v1.Vision;
import com.google.api.services.vision.v1.VisionRequestInitializer;
import com.google.api.services.vision.v1.model.AnnotateImageRequest;
import com.google.api.services.vision.v1.model.BatchAnnotateImagesRequest;
import com.google.api.services.vision.v1.model.BatchAnnotateImagesResponse;
import com.google.api.services.vision.v1.model.EntityAnnotation;
import com.google.api.services.vision.v1.model.Feature;
import com.google.api.services.vision.v1.model.Image;
//import com.splunk.mint.Mint;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class QuizActivity extends AppCompatActivity {
    private static final String TAG = "Tagg";
    public  Context context;
    String comparison1;
    public  String comparisonArray[];
    int list_size=0;
    int picture_inc = 0;
    private static final int GALLERY_IMAGE_REQUEST = 1;
    public static final int CAMERA_PERMISSIONS_REQUEST = 2;
    public static final int CAMERA_IMAGE_REQUEST = 3;
    String description;
    public static final String FILE_NAME = "temp.jpg";

    ImageView mImageShow;
    DatabaseHelper databaseHelper;
    SharedPreferences sharedPreferences;
    ArrayList<AdventureModel> arrayList = new ArrayList<>();
    ProgressBar progressBar;
    TextView mImageDetails;
    MediaPlayer success_sound,failure_sound;

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quiz_activity);

        databaseHelper = new DatabaseHelper(this);
     //   Mint.initAndStartSession(this.getApplication(), "0be61e22");

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        success_sound = MediaPlayer.create(this,R.raw.success_sound);
        success_sound.setAudioStreamType(AudioManager.STREAM_MUSIC);
        failure_sound = MediaPlayer.create(this,R.raw.failure_sound);
        failure_sound.setAudioStreamType(AudioManager.STREAM_MUSIC);

        arrayList = databaseHelper.getAllAdventures();

        comparisonArray = arrayList.get(picture_inc).getRiddleResult().split(",");

        description = arrayList.get(picture_inc).getRiddleDesc();

        list_size = arrayList.size();



        final ContextThemeWrapper contextThemeWrapper = new ContextThemeWrapper(QuizActivity.this, android.R.style.Theme_DeviceDefault_Light_Dialog);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buildDialog();
            }
        });


        mImageDetails = (TextView) findViewById(R.id.image_details);
        mImageShow = (ImageView) findViewById(R.id.main_image);
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);

        mImageDetails.setText(arrayList.get(0).getRiddleDesc());

        showInstructionPopup(getResources().getString(R.string.welcoming_header) + PreferenceManager.getDefaultSharedPreferences(this).getString("inst_name","") + " quest", getResources().getString(R.string.live_inst));

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        showExitPopup(getResources().getString(R.string.exit_header),getResources().getString(R.string.exit_body));

        return super.onKeyDown(keyCode, event);

    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public  void showInstructionPopup(  String title, String message){

        new android.app.AlertDialog.Builder(QuizActivity.this)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // continue with delete
                        //   ((Activity) context).finish();
                    }
                })
                .setIcon(getResources().getDrawable(R.drawable.ic_play_for_work_black_24dp))
                .show();
    }
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public  void showExitPopup(  String title, String message){

        new android.app.AlertDialog.Builder(QuizActivity.this)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(QuizActivity.this,MainSelectionAct.class);
                        startActivity(intent);
                        finish();
                        // continue with delete
                        //   ((Activity) context).finish();
                    }
                }).setNegativeButton(android.R.string.no,new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // continue with delete
                //   ((Activity) context).finish();
            }
        })
                .setIcon(getResources().getDrawable(android.R.drawable.ic_dialog_alert))
                .show();
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GALLERY_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null) {
            uploadImage(data.getData(),mImageShow,this,mImageDetails);
        } else if (requestCode == CAMERA_IMAGE_REQUEST && resultCode == Activity.RESULT_OK) {
            uploadImage(Uri.fromFile(getCameraFile()),mImageShow,this,mImageDetails);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (PermissionUtilities.permissionGranted(
                requestCode,
                CAMERA_PERMISSIONS_REQUEST,
                grantResults)) {
            startCamera();
        }
    }


    public void startGalleryChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);


        startActivityForResult(Intent.createChooser(intent, "Select a photo"),
                GALLERY_IMAGE_REQUEST);
    }


    public void startCamera() {
        if (PermissionUtilities.requestPermission(
                QuizActivity.this,
                CAMERA_PERMISSIONS_REQUEST,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA)) {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(getCameraFile()));
            startActivityForResult(intent, CAMERA_IMAGE_REQUEST);
        }
    }



    public void uploadImage(Uri uri, ImageView mMainImage,Context context,TextView textView) {
        if (uri != null) {
            try {
                Bitmap bitmap =
                        scaleBitmapDown(
                                MediaStore.Images.Media.getBitmap(context.getContentResolver(), uri),
                                1200);

                callCloudVision(bitmap,textView,comparison1);
                ExifInterface exif = new ExifInterface(uri.getPath());
               double orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, 1);
                Matrix matrix = new Matrix();
                int currentOrientation = getResources().getConfiguration().orientation;
                if (currentOrientation == Configuration.ORIENTATION_LANDSCAPE) {
                    matrix.postRotate(0);

                }
                else {
                    matrix.postRotate(90);

                }
             Bitmap   rotatedBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
                mMainImage.setImageBitmap(rotatedBitmap);
                progressBar.setVisibility(View.VISIBLE);


            } catch (IOException e) {
                Log.d(TAG, "Image picking failed because " + e.getMessage());
                Toast.makeText(context, "error", Toast.LENGTH_LONG).show();
            }
        } else {
            Log.d(TAG, "Image picker gave us a null image.");
            Toast.makeText(context, "error", Toast.LENGTH_LONG).show();
        }
    }


    private void callCloudVision(final Bitmap bitmap, final TextView mImageDetails, final String comparison) throws IOException {

        // Switch text to loading

        mImageDetails.setText(getResources().getString(R.string.process_text));

        // Do the real work in an async task, because we need to use the network anyway
        new AsyncTask<Object, Void, String>() {
            @Override
            protected String doInBackground(Object... params) {
                try {
                    HttpTransport httpTransport = AndroidHttp.newCompatibleTransport();
                    JsonFactory jsonFactory = GsonFactory.getDefaultInstance();

                    Vision.Builder builder = new Vision.Builder(httpTransport, jsonFactory, null);
                    builder.setVisionRequestInitializer(new
                            VisionRequestInitializer(BuildConfig.VISION_API_KEY));

                    Vision vision = builder.build();

                    BatchAnnotateImagesRequest batchAnnotateImagesRequest =
                            new BatchAnnotateImagesRequest();
                    batchAnnotateImagesRequest.setRequests(new ArrayList<AnnotateImageRequest>() {{
                        AnnotateImageRequest annotateImageRequest = new AnnotateImageRequest();

                        // Add the image
                        Image base64EncodedImage = new Image();
                        // Convert the bitmap to a JPEG
                        // Just in case it's a format that Android understands but Cloud Vision
                        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 90, byteArrayOutputStream);
                        byte[] imageBytes = byteArrayOutputStream.toByteArray();

                        // Base64 encode the JPEG
                        base64EncodedImage.encodeContent(imageBytes);


                        annotateImageRequest.setImage(base64EncodedImage);

                        // add the features we want
                        annotateImageRequest.setFeatures(new ArrayList<Feature>() {{
                            Feature labelDetection = new Feature();
                            labelDetection.setType("LABEL_DETECTION");
                            labelDetection.setMaxResults(10);
                            add(labelDetection);
                        }});

                        // Add the list of one thing to the request
                        add(annotateImageRequest);
                    }});

                    Vision.Images.Annotate annotateRequest =
                            vision.images().annotate(batchAnnotateImagesRequest);
                    // Due to a bug: requests to Vision API containing large images fail when GZipped.
                    annotateRequest.setDisableGZipContent(true);
                    Log.d(TAG, "created Cloud Vision request object, sending request");

                    BatchAnnotateImagesResponse response = annotateRequest.execute();
                    return convertResponseToString(response);

                } catch (GoogleJsonResponseException e) {
                    Log.d(TAG, "failed to make API request because " + e.getContent());

                } catch (IOException e) {
                    Log.d(TAG, "failed to make API request because of other IOException " +
                            e.getMessage());

                }

                return "failed";
            }

            protected void onPostExecute(String result) {



                int counter = 0;
                progressBar.setVisibility(View.GONE);

                String[] resultArray = result.split(",");

                for(int i=0;i<resultArray.length;i++){
                    Log.d("detect",resultArray[i]);
                    if(!resultArray[i].equals("")) {
                        String tempComp = resultArray[i];
                        Log.d("ind", "" + i);

                        for (int j = 0; j < comparisonArray.length; j++) {
                            if (comparisonArray[j].contains(tempComp)) {
                                counter++;
                            }
                        }
                    }

                }
                double percentage = Math.round((counter * 100.0) / comparisonArray.length);


                if(result.equals("failed")){
                    failure_sound.start();
                    buildCompletionDialog(false,context,"Processing failed, please check your internet connection");
                    mImageDetails.setText(arrayList.get(picture_inc).getRiddleDesc());
                }else
                if(percentage>=30) {
                    success_sound.start();
                    buildCompletionDialog(true,context,"Congratulations, you found the  clue");
                }else if(percentage>=10&&percentage<=30){
                    failure_sound.start();
                    buildCompletionDialog(false,context,"Close, maybe try with different angle?");
                    mImageDetails.setText(arrayList.get(picture_inc).getRiddleDesc());

                }else {
                    failure_sound.start();
                    buildCompletionDialog(false,context,"Sorry wrong picture");
                    mImageDetails.setText(arrayList.get(picture_inc).getRiddleDesc());
                }
            }
        }.execute();
    }

    public Bitmap scaleBitmapDown(Bitmap bitmap, int maxDimension) {

        int originalWidth = bitmap.getWidth();
        int originalHeight = bitmap.getHeight();
        int resizedWidth = maxDimension;
        int resizedHeight = maxDimension;

        if (originalHeight > originalWidth) {
            resizedHeight = maxDimension;
            resizedWidth = (int) (resizedHeight * (float) originalWidth / (float) originalHeight);
        } else if (originalWidth > originalHeight) {
            resizedWidth = maxDimension;
            resizedHeight = (int) (resizedWidth * (float) originalHeight / (float) originalWidth);
        } else if (originalHeight == originalWidth) {
            resizedHeight = maxDimension;
            resizedWidth = maxDimension;
        }
        return Bitmap.createScaledBitmap(bitmap, resizedWidth, resizedHeight, false);
    }

    private String convertResponseToString(BatchAnnotateImagesResponse response) {
        String message = "";

        List<EntityAnnotation> labels = response.getResponses().get(0).getLabelAnnotations();
        if (labels != null) {
            for (EntityAnnotation label : labels) {
             //   message += String.format("%.3f: %s", label.getScore(), label.getDescription());
                message += String.format(label.getDescription());
                message += ",";
            }
        } else {
            message += "nothing";
        }

        return message;
    }

    public File getCameraFile() {
        File dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        return new File(dir, FILE_NAME);
    }

    public void buildDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(QuizActivity.this);
        builder
                .setMessage("Select")
                .setPositiveButton("galerija", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startGalleryChooser();
                    }
                })
                .setNegativeButton("camera", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startCamera();
                    }
                });
        builder.create().show();
    }

    public  void completionProceed(boolean passed){

        
        if(passed) {
            picture_inc++;

            //PreferenceManager.getDefaultSharedPreferences(context).edit().putInt("picture_inc",picture_inc).apply();

            if(picture_inc==list_size) {

                databaseHelper.updatePassedState(sharedPreferences.getString("inst_name",""),1);
                Intent intent = new Intent(QuizActivity.this, RewardAvt.class);
                startActivity(intent);
              finish();
            }else {

                mImageDetails.setText(arrayList.get(picture_inc).getRiddleDesc());
                mImageShow.setImageDrawable(getResources().getDrawable(R.drawable.question_mark));

                comparisonArray = arrayList.get(picture_inc).getRiddleResult().split(",");



            }

        }

    }


    public void buildCompletionDialog(final boolean passed, final  Context context2, String Message){
        AlertDialog.Builder builder = new AlertDialog.Builder(QuizActivity.this);
        builder
                .setMessage(Message)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        completionProceed(passed);
                    }
                });

        builder.create().show();
    }
}