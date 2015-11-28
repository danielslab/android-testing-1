package com.example.android.testing.notes.util;

import android.support.design.widget.Snackbar;

import com.example.android.testing.notes.R;

import it.cosenonjaviste.mv2m.Mv2mView;

public class SnackbarMessageManager {

    public void showMessage(Mv2mView view, int message) {
        if (view != null) {
            Snackbar.make(view.getActivity().findViewById(R.id.fab_add_notes), message, Snackbar.LENGTH_LONG).show();
        }
    }
}