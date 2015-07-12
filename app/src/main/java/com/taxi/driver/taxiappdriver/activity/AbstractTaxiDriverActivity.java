package com.taxi.driver.taxiappdriver.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;


public class AbstractTaxiDriverActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    protected void showMessage(String message) {
        Toast.makeText(AbstractTaxiDriverActivity.this, message, Toast.LENGTH_SHORT).show();
    }

    protected void startScreen(Class<?> activity) {
        Intent intent = new Intent(AbstractTaxiDriverActivity.this, activity);
        startActivity(intent);
    }
}
