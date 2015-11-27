package com.example.android.testing.notes.util;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;

public class Navigator {

    public boolean isCameraInstalled(Activity activity) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        return takePictureIntent.resolveActivity(activity.getPackageManager()) != null;
    }

    public void openCamera(String saveTo, Activity activity, int requestCode) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.parse(saveTo));
        activity.startActivityForResult(takePictureIntent, requestCode);
    }

    public void finish(Activity activity, int resultCode) {
        activity.setResult(resultCode);
        activity.finish();
    }
}
