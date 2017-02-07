package com.appspot.istria.histriapp.View;

import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageButton;

import com.appspot.istria.histriapp.ImageService.RoundedImageView;
import com.appspot.istria.histriapp.R;
import com.facebook.FacebookSdk;
import com.splunk.mint.Mint;

public class RewardAvt extends AppCompatActivity {
    ImageButton imageButton;

    RoundedImageView roundedImageView;
    MediaPlayer success_sound;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        Intent intent = new Intent(RewardAvt.this,MainSelectionAct.class);
        startActivity(intent);
        finish();
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());

        setContentView(R.layout.activity_reward_avt);
       // Mint.initAndStartSession(this.getApplication(), "0be61e22");


        success_sound = MediaPlayer.create(this,R.raw.applause);

        success_sound.start();
      //  imageButton = (ImageButton) findViewById(R.id.image_to_Share);
    }


    public void go_back(View view){
        Intent intent = new Intent(this,MainSelectionAct.class);
        startActivity(intent);
        finish();
    }
    public void share(View view){
       /* Bitmap bm = BitmapFactory.decodeResource(getResources(), R.drawable.images);
        SharePhoto photo = new SharePhoto.Builder()
                .setBitmap(bm)
                .build();
        SharePhotoContent content = new SharePhotoContent.Builder()
                .addPhoto(photo)
                .build();
        ShareDialog shareDialog = new ShareDialog(RewardAvt.this);
        shareDialog.show(content, ShareDialog.Mode.AUTOMATIC);*/
    }
}
