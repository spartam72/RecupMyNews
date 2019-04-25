package com.example.spart.recupmynews.Utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class InternetConnectionState {

    public static boolean noInternetAccess(Context context){
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        assert cm != null;
        NetworkInfo network = cm.getActiveNetworkInfo();
        return network == null || !network.isConnectedOrConnecting();
    }
}
