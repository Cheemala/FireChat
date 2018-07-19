package com.trackbuzz.firebase.networkapi;

import android.content.SharedPreferences;
import android.util.Log;

/**
 * Created by CheemalaCh on 4/15/2018.
 */

public class APIUtils {

    private static String BASE_URL = "https://fir-fe7dd.firebaseio.com/";
    private SharedPreferences appPrfs;
    private SharedPreferences.Editor editor;

    private APIUtils() {
    }

    public static APIServices getAPIService() {
        Log.d("url: ", "" + BASE_URL);

        return RetrofitClient.getClient(BASE_URL).create(APIServices.class);
    }

}
