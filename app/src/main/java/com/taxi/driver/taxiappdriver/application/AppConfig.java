package com.taxi.driver.taxiappdriver.application;

import android.app.Application;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by sharma on 13/6/15.
 */
public class AppConfig extends Application {

    private static AppConfig mInstance;
    private String TAG = "taxi_app";
    private RequestQueue requestQueue;

    public static AppConfig getInstance() {
        return mInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        init();
    }

    private void init() {
        mInstance = AppConfig.this;
    }

    public RequestQueue getRequestque() {
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(getApplicationContext());
        }
        return requestQueue;
    }

    public void cancelPendingRequests(Object tag) {
        if (requestQueue != null) {
            requestQueue.cancelAll(tag);
        }
    }

    public <T> void addToRequestque(Request<T> tag) {
        tag.setTag(TAG);
        getRequestque().add(tag);
    }

    public <T> void addToRequestque(Request<T> tag, String str) {
        tag.setTag(TextUtils.isEmpty(str) ? TAG : str);
        getRequestque().add(tag);
    }

}
