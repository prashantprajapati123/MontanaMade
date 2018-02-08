package com.offer.montanaMade.vollyrequest;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;


/**
 * Created by Rohit on 9/17/2015.
 */
public class M {
    public static void I(Context cx, Class<?> startActivity, Bundle data) {
        Intent i = new Intent(cx, startActivity);
        if (data != null)
            i.putExtras(data);
        cx.startActivity(i);
    }

    public static void E(String msg) {
        Log.e("Log.E By Rohit ", msg);
    }


    public static void T(Context c, String msg) {
        Toast.makeText(c, msg, Toast.LENGTH_SHORT).show();
    }


    public static MaterialDialog initProgressDialog(Context c) {
        return new MaterialDialog.Builder(c)
                .title("Please wait...")
                .autoDismiss(false)
                .content("Wait for a moment.")
                .progress(true, 1)
                .show();
    }


    public static void share(Context c, String subject, String shareBody) {
        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        sharingIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
        sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
        c.startActivity(Intent.createChooser(sharingIntent, "Share via"));
    }
}