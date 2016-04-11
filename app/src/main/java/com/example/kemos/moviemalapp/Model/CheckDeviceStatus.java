package com.example.kemos.moviemalapp.Model;

import android.content.Context;
import android.content.res.Configuration;
import android.net.ConnectivityManager;


/**
 * Created by kemos on 4/8/2016.
 */
public abstract  class CheckDeviceStatus {
    public static boolean isNetworkAvailable(Context context){

        ConnectivityManager connectivityManager = ((ConnectivityManager)  context.getSystemService(
                Context.CONNECTIVITY_SERVICE));
        return connectivityManager.getActiveNetworkInfo()!= null && connectivityManager.getActiveNetworkInfo().isConnected();

    }


}
