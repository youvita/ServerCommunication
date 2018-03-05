package com.dev.kh.connectionsever.helper.ui;

/*
 * Created by ChanYouvita on 8/24/17.
 */

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class BizUtils {

    public static boolean getNetworkStatus(Activity atvt) {
        ConnectivityManager Connectivity = (ConnectivityManager)atvt.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = Connectivity.getActiveNetworkInfo(); //인터넷 연결상태
        if (netInfo == null || !netInfo.isConnectedOrConnecting())
            return false;
        else
            return true;
    }
}
