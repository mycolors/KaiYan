package com.fengniao.kaiyan.util;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetWorkUtils {

    public static final int NO_CONNECTION = -1;

    public static final int NET_WIFI = 0;

    public static final int NET_MOBILE = 0;


    public static boolean isConnect(Context context) {
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (manager == null) {
            return false;
        }
        NetworkInfo[] infos = manager.getAllNetworkInfo();
        if (infos != null) {
            for (NetworkInfo info : infos) {
                if (info.getState() == NetworkInfo.State.CONNECTED) {
                    return true;
                }
            }
            return false;
        }
        return false;
    }


    public static boolean isMobile(Context context) {
        return NET_MOBILE == getNetType(context);
    }

    public static boolean isWifi(Context context) {
        return NET_WIFI == getNetType(context);
    }


    public static int getNetType(Context context) {
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (manager != null) {
            NetworkInfo info = manager.getActiveNetworkInfo();
            if (info != null) {
                if ("WIFI".equalsIgnoreCase(info.getTypeName())) {
                    return NET_WIFI;
                } else if (info.getTypeName().contains("MOBILE")
                        || info.getTypeName().contains("mobile")) {
                    return NET_MOBILE;
                }
            } else {
                return NO_CONNECTION;
            }
        }
        return NO_CONNECTION;
    }

}
