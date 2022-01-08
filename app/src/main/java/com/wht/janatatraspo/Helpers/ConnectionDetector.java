package com.wht.janatatraspo.Helpers;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by Sachin on 10/2/16.
 */
public class ConnectionDetector {
    private static ConnectionDetector connectionDetector = null;
    private Context context;

    private ConnectionDetector(Context context) {
        this.context = context;
    }

    public static ConnectionDetector getInstance(Context context) {
        if (connectionDetector == null) {
            connectionDetector = new ConnectionDetector(context);
        }
        return connectionDetector;
    }

    public boolean isConnectionAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            NetworkInfo networkInfos = connectivityManager.getActiveNetworkInfo();
            if (networkInfos != null && networkInfos.isConnectedOrConnecting()) {
                return true;
            }
        }
        return false;
    }

    public static boolean checkConnection(Context context) {
        return  ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo() != null;
    }
}
