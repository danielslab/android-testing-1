package com.example.android.testing.notes.util;

import android.app.Application;
import android.content.res.Resources;
import android.support.annotation.StringRes;

public class StringTranslator {
    private Resources resources;

    public StringTranslator(Application application) {
        resources = application.getResources();
    }

    public String getString(@StringRes int resId) {
        return resources.getString(resId);
    }
}
