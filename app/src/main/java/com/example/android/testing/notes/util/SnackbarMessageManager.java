package com.example.android.testing.notes.util;

import android.app.Activity;
import android.support.design.widget.Snackbar;

public class SnackbarMessageManager {

    public void showMessage(Activity activity, int message) {
        if (activity != null) {
            Snackbar.make(activity.findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG).show();
        }
    }
}