package saloon.com.saloon.common;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import saloon.com.saloon.MyApplication;

public class Utils {

    public boolean isInternetConnected() {

        boolean isConnected = true;
        ConnectivityManager connectivityManager = (ConnectivityManager) MyApplication
                .getAppContext().getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetInfo = connectivityManager
                .getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        NetworkInfo activeWifiInfo = connectivityManager
                .getNetworkInfo(ConnectivityManager.TYPE_WIFI);

        boolean isconnected = (activeNetInfo != null && activeNetInfo
                .isConnectedOrConnecting())
                || (activeWifiInfo != null && activeWifiInfo
                .isConnectedOrConnecting());

        if (isconnected) {
            isConnected = true;
        } else {
            isConnected = false;
        }
        return isConnected;
    }
}
