package com.easyfitness.utils;

import android.graphics.Color;
import android.view.View;

import com.google.android.material.snackbar.Snackbar;

public class SnackbarDisplayer {

    private static final int GREEN = Color.argb(255,83,166,83);
    private static final int RED = Color.argb(255,204,0,0);

    public static void success(View contextView, String messageText) {
        Snackbar sb = Snackbar.make(contextView, messageText, Snackbar.LENGTH_SHORT);
        sb.setBackgroundTint(SnackbarDisplayer.GREEN);
        sb.show();
    }

    public static void error(View contextView, String messageText) {
        Snackbar sb = Snackbar.make(contextView, messageText, Snackbar.LENGTH_SHORT);
        sb.setBackgroundTint(SnackbarDisplayer.RED);
        sb.show();
    }
}
