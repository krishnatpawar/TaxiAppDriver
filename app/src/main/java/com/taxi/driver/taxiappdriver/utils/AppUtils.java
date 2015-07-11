package com.taxi.driver.taxiappdriver.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;


/**
 * Created by sharma on 13/6/15.
 */
public class AppUtils {

    public static final String strName = "Koti";
    /**
     * checking n/w available or not
     * */
    public static boolean chkStatus(Context context) {
        ConnectivityManager connManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connManager != null) {
            NetworkInfo i = connManager.getActiveNetworkInfo();
            if (i != null) {
                if (i.isConnected())
                    return true;
                if (i.isAvailable())
                    return true;
            }
        }
        return false;
    }


    //validating email
    public static boolean isValidEmail(CharSequence target) {
        if (target == null) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }
    }
}
