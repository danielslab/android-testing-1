package com.example.android.testing.notes.util;

import android.app.Application;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;

import it.cosenonjaviste.mv2m.Mv2mView;

public class Navigator {

    private Application application;

    public Navigator(Application application) {
        this.application = application;
    }

    public boolean isCameraInstalled() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        return takePictureIntent.resolveActivity(application.getPackageManager()) != null;
    }

    public void openCamera(String saveTo, Mv2mView view, int requestCode) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.parse(saveTo));
        view.startActivityForResult(takePictureIntent, requestCode);
    }

    public void finish(Mv2mView view, int resultCode) {
        view.getActivity().setResult(resultCode);
        view.getActivity().finish();
    }
}
