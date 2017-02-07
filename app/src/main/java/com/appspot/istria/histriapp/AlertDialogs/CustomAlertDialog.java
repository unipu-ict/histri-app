package com.appspot.istria.histriapp.AlertDialogs;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;

import com.appspot.istria.histriapp.R;


/**
 * Created by bozidarkokot on 27/12/16.
 */
public class CustomAlertDialog {

    public static void showErrorAlert(final Context context, final Intent intent,String message){
        new AlertDialog.Builder(context)
                .setTitle("Error")
                .setMessage(message)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // continue with delete
                        context.startActivity(intent);
                         ((Activity) context).finish();
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public static void showInstructionPopup(final Context context, String title, String message){

        new AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // continue with delete
                        //   ((Activity) context).finish();
                    }
                })
                .setIcon(context.getResources().getDrawable(R.drawable.ic_play_for_work_black_24dp))
                .show();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public static void showInstitutionPopup(final Context context, final Intent intent, String message, String title){

        new AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // continue with delete
                        context.startActivity(intent);
                     //   ((Activity) context).finish();
                    }
                }).setNegativeButton(android.R.string.no,new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // continue with delete
              dialog.dismiss();
            }
        })
                .setIcon(context.getResources().getDrawable(R.drawable.ic_location_city_black_24dp))
                .show();
    }
}
