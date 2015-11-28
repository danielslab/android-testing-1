package com.example.android.testing.notes.util;

import android.support.design.widget.Snackbar;

import it.cosenonjaviste.mv2m.Mv2mView;

public class SnackbarMessageManager {

    public void showMessage(Mv2mView view, int message) {
        if (view != null) {
            Snackbar.make(view.getActivity().findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG).show();
        }
    }
}