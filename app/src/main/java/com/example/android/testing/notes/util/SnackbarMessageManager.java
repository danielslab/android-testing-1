package com.example.android.testing.notes.util;

import android.support.design.widget.Snackbar;

import com.example.android.testing.notes.R;

import it.cosenonjaviste.mv2m.ActivityHolder;

public class SnackbarMessageManager {

    public void showMessage(ActivityHolder activityHolder, int message) {
        if (activityHolder != null) {
            Snackbar.make(activityHolder.getActivity().findViewById(R.id.fab_add_notes), message, Snackbar.LENGTH_LONG).show();
        }
    }
}