package com.taxi.driver.taxiappdriver.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.taxi.driver.taxiappdriver.R;
import com.taxi.driver.taxiappdriver.application.AppConfig;
import com.taxi.driver.taxiappdriver.utils.Preferences;


public class SplashActivity extends AbstractTaxiDriverActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        AppConfig.setCurentContext(this);
        loginIntoApp(MainActivity.class);
    }

    private void loginIntoApp(final Class<?> toClass) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                    if(!Preferences.getUserId(SplashActivity.this).isEmpty()){
                        startScreen(MainActivity.class);
                        finish();
                    }else{
                        startScreen(StartActivity.class);
                        finish();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    @Override
    protected void onResume() {
        super.onResume();
        AppConfig.setCurentContext(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        AppConfig.setCurentContext(this);
    }
}
