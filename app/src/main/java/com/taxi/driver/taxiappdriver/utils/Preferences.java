package com.taxi.driver.taxiappdriver.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by sharma on 13/6/15.
 */
public class Preferences {

    private static SharedPreferences getSharedPreferences(Context context) {
        return context.getSharedPreferences(Constants.KEY_APP_PREFERENCES, Context.MODE_PRIVATE);
    }

    //saving user id.
    public static void setUserId(Context context, String userId) {
        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putString(Constants.KEY_USER_ID, userId);
        editor.commit();
    }

    public static String getUserId(Context context) {
        return getSharedPreferences(context).getString(Constants.KEY_USER_ID, "");
    }


    public static void setUserEmail(Context context, String email) {
        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putString(Constants.KEY_USER_EMAIL, email);
        editor.commit();
    }

    public static String getUserEmail(Context context) {
        return getSharedPreferences(context).getString(Constants.KEY_USER_EMAIL, "");
    }


    public static void setUserName(Context context, String name) {
        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putString(Constants.KEY_USER_NAME, name);
        editor.commit();
    }

    public static String getUserName(Context context) {
        return getSharedPreferences(context).getString(Constants.KEY_USER_NAME, "");
    }

    public static void setUserPhNum(Context context, String phnum) {
        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putString(Constants.KEY_USER_PHNUM, phnum);
        editor.commit();
    }

    public static String getUserPhNum(Context context) {
        return getSharedPreferences(context).getString(Constants.KEY_USER_PHNUM, "");
    }

}
