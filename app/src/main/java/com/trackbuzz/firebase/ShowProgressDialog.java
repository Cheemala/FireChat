package com.trackbuzz.firebase;

import android.app.ProgressDialog;
import android.content.Context;

/**
 * Created by CheemalaCh on 7/7/2018.
 */

public class ShowProgressDialog {
    private static ProgressDialog progressDialog = null;

    public static void showProgressDialog(Context context, String msg) {
        if (progressDialog == null)
            progressDialog = new ProgressDialog(context);
        progressDialog.setMessage(msg);
        progressDialog.show();
    }

    public static void dismissProgressDialog() {
        if (progressDialog != null)
            progressDialog.dismiss();
    }
}
