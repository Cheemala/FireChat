package com.trackbuzz.firebase;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by CheemalaCh on 7/7/2018.
 */

public class ShowMsg {

    public static void showMessage(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

}
