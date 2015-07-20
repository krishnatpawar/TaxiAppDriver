package com.taxi.driver.taxiappdriver.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;


public class AbstractTaxiDriverActivity extends ActionBarActivity {

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
        finish();
    }

    protected void closeApp(Class<?> activity) {
        Intent intent = new Intent(AbstractTaxiDriverActivity.this, activity);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("LOGOUT", true);
        startActivity(intent);
        finish();
    }

}
